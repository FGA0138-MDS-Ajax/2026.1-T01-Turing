package br.com.seuespacounb.turing.dto.request;

import br.com.seuespacounb.turing.entity.TipoUsuario;

public record AtualizarUsuarioRequestDTO(
        String name,
        String email,
        String cpf,
        String senha,
        TipoUsuario tipoUsuario
) {
}