package br.com.seuespacounb.turing.dto.request;

<<<<<<< Updated upstream
=======
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
>>>>>>> Stashed changes
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoRequestDTO(
<<<<<<< Updated upstream
        @NotBlank(message = "O campo 'motivo' não pode está vazio")
        String motivoSolicitacao,
        @NotNull(message = "O campo 'horarioId' não pode está vazio")
        Long horarioId,
        @NotNull(message = "O campo 'usuarioId' não pode está vazio")
        Long usuarioId
) {}
=======
        @NotNull(message = "O campo 'horarioSalaId' não pode estar vazio")
        Long horarioSalaId,

        @NotNull(message = "O campo 'dataUso' não pode estar vazio")
        @FutureOrPresent(message = "A data de uso não pode ser no passado")
        LocalDate dataUso,

        @NotBlank(message = "O campo 'motivo' não pode estar vazio")
        String motivo,

        @NotNull(message = "A quantidade de participantes é obrigatória")
        @Min(value = 1, message = "A quantidade de participantes deve ser pelo menos 1")
        Integer quantidadeParticipantes
) {}
>>>>>>> Stashed changes
