package br.com.seuespacounb.turing.dto.request;

import br.com.seuespacounb.turing.entity.TipoUsuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotEmpty(message = "Nome é obrigatório")
        String name,

        @NotEmpty(message = "Email é obrigatório")
        String email,

        @Size(
                min = 11,
                max = 11,
                message = "O CPF deve conter os 11 números."
        )
        @NotEmpty(message = "Cpf é obrigatório")
        String cpf,

        @Size(
                min = 8,
                max = 20,
                message = "A senha deve ter entre 8 e 20 caracteres."
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.#_-])[A-Za-z\\d@$!%*?&.#_-]{8,20}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial."
        )
        @NotEmpty(message = "Senha é obrigatória")
        String password
) {
}
