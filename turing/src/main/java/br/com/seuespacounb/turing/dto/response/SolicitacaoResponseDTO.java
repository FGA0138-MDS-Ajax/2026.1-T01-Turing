package br.com.seuespacounb.turing.dto.response;

import br.com.seuespacounb.turing.entity.StatusSolicitacao;

import java.time.LocalDateTime;

public record SolicitacaoResponseDTO(
        Long id,
        String motivoSolicitacao,
        LocalDateTime dataSolicitacao,
        StatusSolicitacao status,
        String justificativa,
        Long usuarioId,
        Long horarioId
) {}