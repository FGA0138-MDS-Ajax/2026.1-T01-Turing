package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.FiltroSolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.request.JustificaticaSolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.request.SolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.*;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.mapstruct.SolicitacaoMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SolicitacaoRepository;
import br.com.seuespacounb.turing.repository.UsuarioRepository;
import br.com.seuespacounb.turing.specification.SolicitacaoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {
    private final HorarioSalaRepository horarioSalaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final SolicitacaoMapper mapper;

    public SolicitacaoResponseDTO salvarSolicitacao(SolicitacaoRequestDTO solicitacaoRequest)throws NotFoundException, ConflictException {
        Solicitacao novaSolicitacao = mapper.paraSolicitacao(solicitacaoRequest);
        HorarioSala horarioSala = horarioSalaRepository
                .findById(novaSolicitacao.getHorario().getId())
                .orElseThrow(()-> new NotFoundException("Não foi possível encontrar o horário escolhido."));
        Usuario usuario = usuarioRepository.findById(novaSolicitacao.getUsuario().getId())
                .orElseThrow(()-> new NotFoundException("Não foi possível encontrar o usuário."));
        verificarConflito(novaSolicitacao);
        novaSolicitacao.setHorario(horarioSala);
        novaSolicitacao.setUsuario(usuario);
        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.saveAndFlush(novaSolicitacao));
    }

    private void verificarConflito(Solicitacao novaSolicitacao)throws ConflictException{
        boolean conflitoHorario = horarioSalaRepository
                .existsByIdAndStatus(
                        novaSolicitacao.getHorario().getId(), StatusHorario.VAGO);
        if(conflitoHorario)
            throw new ConflictException("Não foi possível solicitar este horário, pois ele está ocupado.");

        LocalDateTime dataLimite = LocalDateTime.now().minusWeeks(1);
        boolean conflitoSolicitacao = solicitacaoRepository
                .conflitoSolicitacao(dataLimite, novaSolicitacao.getHorario().getId());

<<<<<<< Updated upstream
        if(conflitoSolicitacao)
            throw new ConflictException("Não foi possível solicitar este horário, pois já foi solicitado no intervalo de uma semana.");
=======
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
                .quantidadeParticipantes(dto.quantidadeParticipantes())
                .build();

        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.saveAndFlush(solicitacao));
>>>>>>> Stashed changes
    }

    @Transactional(readOnly = true)
    public Page<SolicitacaoResponseDTO> filtrarParaUsuario(FiltroSolicitacaoRequestDTO filtro, Long usuarioId, int pagina, int tamanho, String ordenacao, String direcao)throws NotFoundException{
        if (usuarioId == null) throw new NotFoundException("O id do usuário é obrigatório");
        usuarioRepository.findById(usuarioId).orElseThrow(()-> new NotFoundException("O usuáro não foi encontrado"));
        return filtrarOrdenarSolicitacao(filtro, usuarioId, pagina, tamanho, ordenacao,direcao);
    }

    @Transactional(readOnly = true)
    public Page<SolicitacaoResponseDTO> filtrarParaAdm(FiltroSolicitacaoRequestDTO  filtro, int pagina, int tamanho, String ordenacao, String direcao){
        return filtrarOrdenarSolicitacao(filtro, null, pagina, tamanho, ordenacao,direcao);
    }

    private Page<SolicitacaoResponseDTO> filtrarOrdenarSolicitacao(FiltroSolicitacaoRequestDTO  filtro, Long usuarioId, int pagina, int tamanho, String ordenacao, String direcao){
        Specification<Solicitacao> spec = combinarFiltros(filtro, usuarioId);
        return ordenarSolicitacao(spec, pagina, tamanho, ordenacao, direcao);
    }

    private Specification<Solicitacao> combinarFiltros(FiltroSolicitacaoRequestDTO  filtro, Long usuarioId){
        return Specification
                .where(SolicitacaoSpecifications.possuiUserId(usuarioId))
                .and(SolicitacaoSpecifications.possuiDataSolicitacao(filtro.dataSolicitacao()))
                .and(SolicitacaoSpecifications.possuiStatus(filtro.status()))
                .and(SolicitacaoSpecifications.possuiDiaSemana(filtro.diaSemana()))
                .and(SolicitacaoSpecifications.possuiNomeSala(filtro.nomeSala()));
    }

    private Page<SolicitacaoResponseDTO> ordenarSolicitacao(Specification<Solicitacao> spec, int pagina, int tamanho, String ordenacao, String direcao){
        List<String> camposPermitidos = List.of("salaNome", "dataSolicitacao");
        if(!camposPermitidos.contains(ordenacao)){
            ordenacao = "dataSolicitacao";
        }
        Sort sort = direcao != null && direcao.equalsIgnoreCase("desc")
                ? Sort.by(ordenacao).descending():
                Sort.by(ordenacao).ascending();
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        Page<Solicitacao> filtradosOrdenados = solicitacaoRepository.findAll(spec, pageable);
        return filtradosOrdenados.map(mapper::paraSolicitacaoResponseDTO);
    }

    @Transactional
    public SolicitacaoResponseDTO aprovarSolicitacao(Long solicitacaoId) throws NotFoundException{
        Solicitacao solicitacao = solicitacaoRepository
                .findById(solicitacaoId)
                .orElseThrow(()-> new NotFoundException("A solicitacao não foi encontrada"));
        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.save(solicitacao));
    }

    @Transactional
    public SolicitacaoResponseDTO rejeitarSolicitacao(Long solicitacaoId, JustificaticaSolicitacaoRequestDTO justificativaSolicitacao) throws NotFoundException{
        Solicitacao solicitacao = solicitacaoRepository
                .findById(solicitacaoId)
                .orElseThrow(()-> new NotFoundException("A solicitacao não foi encontrada"));
        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        solicitacao.setJustificativa(justificativaSolicitacao.justificativa());
        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.save(solicitacao));
    }

    @Transactional
    public SolicitacaoResponseDTO cancelarSolicitacao(Long solicitacaoId, JustificaticaSolicitacaoRequestDTO justificativaSolicitacao) throws NotFoundException{
        Solicitacao solicitacao = solicitacaoRepository
                .findById(solicitacaoId)
                .orElseThrow(()-> new NotFoundException("A solicitacao não foi encontrada"));

                if (solicitacao.getStatus() == StatusSolicitacao.REJEITADA ||
                        solicitacao.getStatus() == StatusSolicitacao.CANCELADA) {
                    throw new ConflictException("Não é possível cancelar uma solicitação que já está " + solicitacao.getStatus().name().toLowerCase() + ".");
                }

                if (solicitacao.getDataUso() != null && solicitacao.getDataUso().isBefore(LocalDate.now().plusDays(1))) {
                    throw new ConflictException("O cancelamento deve ser feito com antecedência mínima de 1 dia.");
                }
                
        solicitacao.setStatus(StatusSolicitacao.CANCELADA);
        solicitacao.setJustificativa(justificativaSolicitacao.justificativa());
        return mapper.paraSolicitacaoResponseDTO(solicitacaoRepository.save(solicitacao));
    }
}
