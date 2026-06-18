package br.com.seuespacounb.turing.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioSalaRequestDTO(
        @NotNull(message = "O campo 'inicioPeriodo' não pode está vazio")
        LocalDate inicioPeriodo,

        @NotNull(message = "O campo 'fimPeriodo' não pode está vazio")
        @Future(message = "A data selecionada não é válida")
        LocalDate fimPeriodo,

        @NotNull(message = "O campo 'diaSemana' não pode está vazio")
        DayOfWeek diaSemana,

        @NotNull(message = "O campo 'inicioHora' não pode está vazio")
        LocalTime inicioHora,

        @NotNull(message = "O campo 'fimHora' não pode está vazio")
        LocalTime fimHora,

        @NotNull(message = "O campo 'status' não pode está vazio")
        String status,

        @NotBlank(message = "O campo 'descriçãoOcupacao' não pode está vazio")
        String descricaoOcupacao,

        @NotNull(message = "O campo 'salaId' não pode está vazio")
        Long salaId
) {}
