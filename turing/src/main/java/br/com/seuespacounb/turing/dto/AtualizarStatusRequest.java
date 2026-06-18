package br.com.seuespacounb.turing.dto;

import br.com.seuespacounb.turing.entity.StatusHorario;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusRequest(
        @NotNull(message = "O novo status é obrigatório")
        StatusHorario status)
{}
