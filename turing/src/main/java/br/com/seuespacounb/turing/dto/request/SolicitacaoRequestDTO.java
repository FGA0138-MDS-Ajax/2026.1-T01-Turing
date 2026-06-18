package br.com.seuespacounb.turing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoRequestDTO(
        @NotBlank(message = "O campo 'motivo' não pode está vazio")
        String motivoSolicitacao,
        @NotNull(message = "O campo 'horarioId' não pode está vazio")
        Long horarioId,
        @NotNull(message = "O campo 'usuarioId' não pode está vazio")
        Long usuarioId
) {}
