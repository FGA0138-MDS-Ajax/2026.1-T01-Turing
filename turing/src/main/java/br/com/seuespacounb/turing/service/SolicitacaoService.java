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

        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.saveAndFlush(solicitacao));
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

            solicitacaoRepository.saveAll(concorrentes);
        }

        solicitacao.setStatus(request.status());

        if (request.observacaoAdm() != null) {
            solicitacao.setObservacaoAdm(request.observacaoAdm());
        }

        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.save(solicitacao));
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
}