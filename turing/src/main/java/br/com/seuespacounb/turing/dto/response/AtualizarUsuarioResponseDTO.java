package br.com.seuespacounb.turing.dto.response;

import br.com.seuespacounb.turing.dto.UsuarioDTO;

public record AtualizarUsuarioResponseDTO(
        UsuarioDTO usuario,
        String token
) {
}