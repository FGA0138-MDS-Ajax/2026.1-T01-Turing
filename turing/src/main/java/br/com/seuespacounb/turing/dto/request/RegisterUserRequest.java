package br.com.seuespacounb.turing.dto.request;

import br.com.seuespacounb.turing.entity.TipoUsuario;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @NotEmpty(message = "Nome é obrigatório")
        String name,
        @NotEmpty(message = "Email é obrigatório")
        String email,
        @NotEmpty(message = "Cpf é obrigatório")
        String cpf,
        @NotEmpty(message = "Senha é obrigatória")
        String password,

        TipoUsuario tipoUsuario
) {
}
