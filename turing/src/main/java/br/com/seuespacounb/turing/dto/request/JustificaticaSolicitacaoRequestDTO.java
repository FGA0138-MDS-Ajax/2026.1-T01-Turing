package br.com.seuespacounb.turing.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JustificaticaSolicitacaoRequestDTO(
        @NotBlank(message = "A justificativa é obrigatória")
        String justificativa
) {}