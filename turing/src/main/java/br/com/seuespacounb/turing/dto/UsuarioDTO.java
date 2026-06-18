package br.com.seuespacounb.turing.dto;

import br.com.seuespacounb.turing.entity.TipoUsuario;

public record UsuarioDTO(
        String name,
        String email,
        String cpf,
        String senha,
        TipoUsuario tipoUsuario
) {
}