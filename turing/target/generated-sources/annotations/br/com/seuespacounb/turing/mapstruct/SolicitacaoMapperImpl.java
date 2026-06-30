package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Solicitacao;
import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import br.com.seuespacounb.turing.entity.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-29T22:40:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class SolicitacaoMapperImpl implements SolicitacaoMapper {

    @Override
    public SolicitacaoResponseDTO paraSolicitacaoResponseDTO(Solicitacao entity) {
        if ( entity == null ) {
            return null;
        }

        Long horarioSalaId = null;
        Long solicitanteId = null;
        Long id = null;
        String motivo = null;
        Integer quantidadeParticipantes = null;
        LocalDateTime dataSolicitacao = null;
        LocalDate dataUso = null;
        StatusSolicitacao status = null;
        String observacaoAdm = null;

        horarioSalaId = entityHorarioSalaId( entity );
        solicitanteId = entitySolicitanteId( entity );
        id = entity.getId();
        motivo = entity.getMotivo();
        quantidadeParticipantes = entity.getQuantidadeParticipantes();
        dataSolicitacao = entity.getDataSolicitacao();
        dataUso = entity.getDataUso();
        status = entity.getStatus();
        observacaoAdm = entity.getObservacaoAdm();

        SolicitacaoResponseDTO solicitacaoResponseDTO = new SolicitacaoResponseDTO( id, motivo, quantidadeParticipantes, dataSolicitacao, dataUso, status, observacaoAdm, horarioSalaId, solicitanteId );

        return solicitacaoResponseDTO;
    }

    @Override
    public List<SolicitacaoResponseDTO> paraListaSolicitacaoResponseDTO(List<Solicitacao> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SolicitacaoResponseDTO> list = new ArrayList<SolicitacaoResponseDTO>( entities.size() );
        for ( Solicitacao solicitacao : entities ) {
            list.add( paraSolicitacaoResponseDTO( solicitacao ) );
        }

        return list;
    }

    private Long entityHorarioSalaId(Solicitacao solicitacao) {
        if ( solicitacao == null ) {
            return null;
        }
        HorarioSala horarioSala = solicitacao.getHorarioSala();
        if ( horarioSala == null ) {
            return null;
        }
        Long id = horarioSala.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entitySolicitanteId(Solicitacao solicitacao) {
        if ( solicitacao == null ) {
            return null;
        }
        Usuario solicitante = solicitacao.getSolicitante();
        if ( solicitante == null ) {
            return null;
        }
        Long id = solicitante.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
