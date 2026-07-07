package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.response.UsuarioResumoDTO;
import br.com.seuespacounb.turing.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResumoDTO paraUsuarioResumoDTO(Usuario usuario);
}