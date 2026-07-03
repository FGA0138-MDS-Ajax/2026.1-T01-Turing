package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.AtualizarStatusSolicitacaoRequest;
import br.com.seuespacounb.turing.dto.request.SolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.*;
import br.com.seuespacounb.turing.exception.BadRequestException;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.exception.UnauthorizedException;
import br.com.seuespacounb.turing.mapstruct.SolicitacaoMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SalaRepository;
import br.com.seuespacounb.turing.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final HorarioSalaRepository horarioSalaRepository;
    private final SalaRepository salaRepository;
    private final SolicitacaoMapper mapper;
    private final EmailService emailService;

    @Transactional
    public SolicitacaoResponseDTO criarSolicitacao(SolicitacaoRequestDTO dto, Usuario solicitante)
            throws NotFoundException, ConflictException {

        HorarioSala horarioSala = horarioSalaRepository.findById(dto.horarioSalaId())
                .orElseThrow(() -> new NotFoundException("Horário não encontrado com id: " + dto.horarioSalaId()));

        Sala sala = salaRepository.findById(horarioSala.getSala().getId())
                .orElseThrow(() -> new NotFoundException("Sala não encontrada com id: " + horarioSala.getSala().getId()));

        if (horarioSala.getDescricaoOcupacao() != null && !horarioSala.getDescricaoOcupacao().isBlank()) {
            throw new ConflictException(
                    "Este horário já está ocupado com aula fixa: " + horarioSala.getDescricaoOcupacao()
            );
        }

        if (!horarioSala.getDiaSemana().equals(dto.dataUso().getDayOfWeek())) {
            throw new ConflictException(
                    "A data informada (" + dto.dataUso() + ") não é uma "
                            + horarioSala.getDiaSemana() + ", que é o dia deste horário."
            );
        }

        if (dto.quantidadeParticipantes() > sala.getCapacidade()){
            throw new BadRequestException("Quantidade de participantes ultrapassa o limite da capacidade da sala");
        }

        boolean temConflito = solicitacaoRepository.existeConflito(
                dto.horarioSalaId(),
                dto.dataUso(),
                List.of(StatusSolicitacao.APROVADA),
                -1L);

        if (temConflito) {
            throw new ConflictException("Já existe uma solicitação aprovada para este horário nesta data.");
        }

        Solicitacao solicitacao = Solicitacao.builder()
                .motivo(dto.motivo())
                .dataSolicitacao(LocalDateTime.now())
                .dataUso(dto.dataUso())
                .horarioSala(horarioSala)
                .solicitante(solicitante)
                .quantidadeParticipantes(dto.quantidadeParticipantes())
                .build();


        // salva primeiro para garantir que o id ja exista antes de montar o email
        Solicitacao solicitacaoSalva = solicitacaoRepository.saveAndFlush(solicitacao);

        // envia comprovante ao criar a solicitação
        Context context = new Context();
        context.setVariable("nomeUsuario", solicitante.getName());
        context.setVariable("id", solicitacao.getId());
        context.setVariable("nomeSala", horarioSala.getSala().getNome());
        context.setVariable("localizacao", horarioSala.getSala().getLocalizacao());
        context.setVariable("dataSolicitacao", solicitacao.getDataUso().toString());
        context.setVariable("horario", horarioSala.getInicioHora() + " - " + horarioSala.getFimHora());
        context.setVariable("dataCriacao", solicitacao.getDataSolicitacao().toString());
        context.setVariable("status", "Em análise");

        emailService.enviarEmailHtml(solicitante.getEmail(), "Comprovante de Solicitação - Seu Espaço UnB", "ComprovanteSolicitacao", context);

        return mapper.paraSolicitacaoResponseDTO(solicitacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoResponseDTO> listarSolicitacoesPorSala(Long salaId) {
        return mapper.paraListaSolicitacaoResponseDTO(
                solicitacaoRepository.findAtivasPorSala(salaId, LocalDate.now())
        );
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoResponseDTO> listarMinhasSolicitacoes(Long solicitanteId) {
        return mapper.paraListaSolicitacaoResponseDTO(
                solicitacaoRepository.findBySolicitanteId(solicitanteId)
        );
    }

    @Transactional
    public SolicitacaoResponseDTO atualizarStatus(Long id, AtualizarStatusSolicitacaoRequest request)
            throws NotFoundException, ConflictException {

        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada com id: " + id));

        if (request.status() == StatusSolicitacao.APROVADA) {
            boolean temConflito = solicitacaoRepository.existeConflito(
                    solicitacao.getHorarioSala().getId(),
                    solicitacao.getDataUso(),
                    List.of(StatusSolicitacao.APROVADA),
                    solicitacao.getId());

            if (temConflito) {
                throw new ConflictException("Já existe uma solicitação aprovada para este horário nesta data.");
            }

            List<Solicitacao> concorrentes = solicitacaoRepository
                    .findConcorrentesPendentes(
                            solicitacao.getHorarioSala().getId(),
                            solicitacao.getDataUso(),
                            solicitacao.getId());

            concorrentes.forEach(c -> {
                c.setStatus(StatusSolicitacao.REJEITADA);
                c.setObservacaoAdm("Rejeitada automaticamente: outra solicitação foi aprovada para este horário.");
            });

            List<Solicitacao> concorrentesSalvos = solicitacaoRepository.saveAll(concorrentes);
            concorrentesSalvos.forEach(this::enviarEmailNotificacao);
        }

        solicitacao.setStatus(request.status());

        if (request.observacaoAdm() != null) {
            solicitacao.setObservacaoAdm(request.observacaoAdm());
        }

        Solicitacao solicitacaoAtualizada = solicitacaoRepository.save(solicitacao);

        // dispara o email para a solicitação principal (aprovada ou rejeitada pelo admin)
        enviarEmailNotificacao(solicitacaoAtualizada);

        return mapper.paraSolicitacaoResponseDTO(solicitacaoAtualizada);
    }

    @Transactional
    public void cancelarSolicitacao(Long id, Usuario solicitante)
            throws NotFoundException, UnauthorizedException, ConflictException {

        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada com id: " + id));

        if (!solicitacao.getSolicitante().getId().equals(solicitante.getId())) {
            throw new UnauthorizedException("Você não tem permissão para cancelar esta solicitação.");
        }

        if (solicitacao.getStatus() == StatusSolicitacao.APROVADA ||
                solicitacao.getStatus() == StatusSolicitacao.REJEITADA ||
                solicitacao.getStatus() == StatusSolicitacao.CANCELADA) {
            throw new ConflictException(
                    "Não é possível cancelar uma solicitação que já está " +
                            solicitacao.getStatus().name().toLowerCase() + "."
            );
        }

        if (solicitacao.getDataUso() != null && solicitacao.getDataUso().isBefore(LocalDate.now().plusDays(1))) {
            throw new ConflictException("O cancelamento deve ser feito com antecedência mínima de 1 dia.");
        }

        solicitacao.setStatus(StatusSolicitacao.CANCELADA);
        solicitacaoRepository.save(solicitacao);
    }

    @Transactional(readOnly = true)
    public SolicitacaoResponseDTO buscarPorId(Long id) throws NotFoundException {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada com id: " + id));

        return mapper.paraSolicitacaoResponseDTO(solicitacao);
    }

    private void enviarEmailNotificacao(Solicitacao solicitacao) {
        String destinatario = solicitacao.getSolicitante().getEmail();
        String assunto;
        String nomeTemplate;

        Context context = new Context();
        context.setVariable("nomeUsuario", solicitacao.getSolicitante().getName());
        context.setVariable("id", solicitacao.getId());
        context.setVariable("nomeSala", solicitacao.getHorarioSala().getSala().getNome());
        context.setVariable("localizacao", solicitacao.getHorarioSala().getSala().getLocalizacao());
        context.setVariable("dataSolicitacao", solicitacao.getDataUso().toString());
        context.setVariable("horario", solicitacao.getHorarioSala().getInicioHora() + " - " + solicitacao.getHorarioSala().getFimHora());
        context.setVariable("dataCriacao", solicitacao.getDataSolicitacao().toString());
        context.setVariable("linkSistema", "http://localhost:3000");
        context.setVariable("linkAgenda", "http://localhost:3000");

        if (solicitacao.getStatus() == StatusSolicitacao.APROVADA) {
            assunto = "Solicitação Aprovada - Seu Espaço UnB";
            nomeTemplate = "SolicitacaoAprovada";

        } else if (solicitacao.getStatus() == StatusSolicitacao.REJEITADA) {
            assunto = "Solicitação Rejeitada - Seu Espaço UnB";
            nomeTemplate = "SolicitacaoNegada";
            context.setVariable("justificativa", solicitacao.getObservacaoAdm() != null
                    ? solicitacao.getObservacaoAdm()
                    : "Nenhuma justificativa fornecida.");
        } else {
            return;
        }

        emailService.enviarEmailHtml(destinatario, assunto, nomeTemplate, context);
    }
}