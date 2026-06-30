package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.Solicitacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {

    @Mapping(source = "horarioSala.id", target = "horarioSalaId")
    @Mapping(source = "solicitante.id", target = "solicitanteId")
    SolicitacaoResponseDTO paraSolicitacaoResponseDTO(Solicitacao entity);

    List<SolicitacaoResponseDTO> paraListaSolicitacaoResponseDTO(List<Solicitacao> entities);
}