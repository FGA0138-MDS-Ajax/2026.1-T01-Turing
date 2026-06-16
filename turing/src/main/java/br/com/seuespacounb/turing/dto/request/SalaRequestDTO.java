package br.com.seuespacounb.turing.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequestDTO(
        @NotBlank(message = "O campo 'nome' não pode estar vazio")
        String nome,

        @NotNull(message = "O campo 'capacidade' não pode estar vazio")
        @Min(value = 1, message = "A capacidade deve ser maior que zero")
        int capacidade,

        @NotBlank(message = "O campo 'localização' não pode estar vazio")
        String localizacao
) {}
