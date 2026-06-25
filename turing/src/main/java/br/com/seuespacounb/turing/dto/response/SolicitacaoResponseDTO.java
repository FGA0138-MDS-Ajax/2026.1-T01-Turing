package br.com.seuespacounb.turing.dto.response;

import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SolicitacaoResponseDTO(
        Long id,
        String motivo,
        LocalDateTime dataSolicitacao,
        LocalDate dataUso,
        StatusSolicitacao status,
        String observacaoAdm,
        Long horarioSalaId,
        Long solicitanteId
) {}