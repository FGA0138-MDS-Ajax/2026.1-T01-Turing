package br.com.seuespacounb.turing.dto.request;

import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusSolicitacaoRequest(
        @NotNull(message = "O novo status é obrigatório")
        StatusSolicitacao status,

        String observacaoAdm // opcional, usado ao rejeitar
) {}