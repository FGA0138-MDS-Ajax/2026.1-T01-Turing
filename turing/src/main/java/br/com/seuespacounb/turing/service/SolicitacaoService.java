package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.AtualizarStatusSolicitacaoRequest;
import br.com.seuespacounb.turing.dto.request.SolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.*;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.exception.UnauthorizedException;
import br.com.seuespacounb.turing.mapstruct.SolicitacaoMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
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
    private final SolicitacaoMapper mapper;

    @Transactional
    public SolicitacaoResponseDTO criarSolicitacao(SolicitacaoRequestDTO dto, Usuario solicitante)
            throws NotFoundException, ConflictException {

        HorarioSala horarioSala = horarioSalaRepository.findById(dto.horarioSalaId())
                .orElseThrow(() -> new NotFoundException("Horário não encontrado com id: " + dto.horarioSalaId()));

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

        boolean temConflito = solicitacaoRepository.existeConflito(
                dto.horarioSalaId(),
                dto.dataUso(),
                List.of(StatusSolicitacao.PENDENTE, StatusSolicitacao.APROVADA),
                -1L);

        if (temConflito) {
            throw new ConflictException("Já existe uma solicitação pendente ou aprovada para este horário nesta data.");
        }

        Solicitacao solicitacao = Solicitacao.builder()
                .motivo(dto.motivo())
                .dataSolicitacao(LocalDateTime.now())
                .dataUso(dto.dataUso())
                .horarioSala(horarioSala)
                .solicitante(solicitante)
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
            throws NotFoundException {

        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada com id: " + id));

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
                solicitacao.getStatus() == StatusSolicitacao.REJEITADA) {
            throw new ConflictException(
                    "Não é possível cancelar uma solicitação já " +
                            solicitacao.getStatus().name().toLowerCase() + "."
            );
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