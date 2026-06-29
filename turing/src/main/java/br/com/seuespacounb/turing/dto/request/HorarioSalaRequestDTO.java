package br.com.seuespacounb.turing.dto.request;

import br.com.seuespacounb.turing.validation.HorarioValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@HorarioValido
public record HorarioSalaRequestDTO(
        @NotNull(message = "O campo 'diaSemana' não pode estar vazio")
        DayOfWeek diaSemana,

        @NotNull(message = "O campo 'inicioHora' não pode estar vazio")
        LocalTime inicioHora,

        @NotNull(message = "O campo 'fimHora' não pode estar vazio")
        LocalTime fimHora,

        String descricaoOcupacao,

        @NotNull(message = "O campo 'salaId' não pode estar vazio")
        Long salaId
) {}