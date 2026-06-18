package br.com.seuespacounb.turing.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record HorarioSalaResponseDTO(
        Long id,
        LocalDate inicioPeriodo,
        LocalDate fimPeriodo,
        DayOfWeek diaSemana,
        LocalTime inicioHora,
        LocalTime fimHora,
        String status,
        String descricaoOcupacao
) {}
