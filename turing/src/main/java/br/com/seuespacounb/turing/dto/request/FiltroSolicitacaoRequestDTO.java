package br.com.seuespacounb.turing.dto.request;

import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public record FiltroSolicitacaoRequestDTO(
        LocalDateTime dataSolicitacao,
        StatusSolicitacao status,
        DayOfWeek diaSemana,
        String nomeSala
) {}
