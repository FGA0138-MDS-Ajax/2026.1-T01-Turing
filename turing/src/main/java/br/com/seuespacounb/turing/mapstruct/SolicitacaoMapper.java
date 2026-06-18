package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.request.SolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.Solicitacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "horario", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "justificativa", ignore = true)
    Solicitacao paraSolicitacao(SolicitacaoRequestDTO solicitacaoRequestDTO);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "horarioId", source = "horario.id")
    SolicitacaoResponseDTO paraSolicitacaoResponseDTO(Solicitacao solicitacao);
}