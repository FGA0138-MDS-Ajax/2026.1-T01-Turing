package br.com.seuespacounb.turing.dto;

import br.com.seuespacounb.turing.entity.TipoUsuario;

public record AdmGetUsuarioDTO(
        Long idUsuario,
        String name,
        String email,
        String cpf,
        TipoUsuario tipoUsuario
) {
}