package br.com.seuespacounb.turing.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SolicitacaoRequestDTO(
        @NotNull(message = "O campo 'horarioSalaId' não pode estar vazio")
        Long horarioSalaId,

        @NotNull(message = "O campo 'dataUso' não pode estar vazio")
        @FutureOrPresent(message = "A data de uso não pode ser no passado")
        LocalDate dataUso,

        @NotBlank(message = "O campo 'motivo' não pode estar vazio")
        String motivo
) {}