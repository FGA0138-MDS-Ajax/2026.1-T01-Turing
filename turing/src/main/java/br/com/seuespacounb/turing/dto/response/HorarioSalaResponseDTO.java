package br.com.seuespacounb.turing.dto.response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioSalaResponseDTO(
        Long id,
        LocalDate inicioPeriodo,
        LocalDate fimPeriodo,
        DayOfWeek diaSemana,
        LocalTime inicioHora,
        LocalTime fimHora,
        String status,
        String descricaoOcupacao,
        Long salaId
) {}
