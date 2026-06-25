package br.com.seuespacounb.turing.dto.request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record FiltroSalaRequest(
        String nome,
        Integer capacidade,
        String localizacao,
        DayOfWeek diaSemana,
        LocalTime inicioHora,
        LocalTime fimHora,
        LocalDate dataUso
) {}