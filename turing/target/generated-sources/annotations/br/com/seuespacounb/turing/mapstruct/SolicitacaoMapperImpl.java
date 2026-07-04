package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.dto.response.UsuarioResumoDTO;
import br.com.seuespacounb.turing.entity.Solicitacao;
import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T00:03:42-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class SolicitacaoMapperImpl implements SolicitacaoMapper {

    @Autowired
    private HorarioSalaMapper horarioSalaMapper;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public SolicitacaoResponseDTO paraSolicitacaoResponseDTO(Solicitacao entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String motivo = null;
        Integer quantidadeParticipantes = null;
        LocalDateTime dataSolicitacao = null;
        LocalDate dataUso = null;
        StatusSolicitacao status = null;
        String observacaoAdm = null;
        HorarioSalaResponseDTO horarioSala = null;
        UsuarioResumoDTO solicitante = null;

        id = entity.getId();
        motivo = entity.getMotivo();
        quantidadeParticipantes = entity.getQuantidadeParticipantes();
        dataSolicitacao = entity.getDataSolicitacao();
        dataUso = entity.getDataUso();
        status = entity.getStatus();
        observacaoAdm = entity.getObservacaoAdm();
        horarioSala = horarioSalaMapper.paraHorarioResponseDTO( entity.getHorarioSala() );
        solicitante = usuarioMapper.paraUsuarioResumoDTO( entity.getSolicitante() );

        SolicitacaoResponseDTO solicitacaoResponseDTO = new SolicitacaoResponseDTO( id, motivo, quantidadeParticipantes, dataSolicitacao, dataUso, status, observacaoAdm, horarioSala, solicitante );

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
}
