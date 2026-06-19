package br.com.seuespacounb.turing.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioSalaResponseDTO(
        Long id,
        DayOfWeek diaSemana,
        LocalTime inicioHora,
        LocalTime fimHora,
        String descricaoOcupacao,
        Long salaId
) {}