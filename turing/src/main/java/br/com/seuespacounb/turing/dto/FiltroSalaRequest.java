package br.com.seuespacounb.turing.dto;

import br.com.seuespacounb.turing.entity.StatusHorario;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record FiltroSalaRequest(
        String nome,
        Integer capacidade,
        String localizacao,
        DayOfWeek diaSemana,
        LocalTime inicioHora,
        LocalTime fimHora,
        StatusHorario status
) {}
