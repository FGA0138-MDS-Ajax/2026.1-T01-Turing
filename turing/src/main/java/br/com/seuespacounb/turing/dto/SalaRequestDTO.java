package br.com.seuespacounb.turing.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SalaRequestDTO(
        @NotBlank(message = "O campo 'nome' não pode estar vazio")
        String nome,

        @NotNull(message = "O campo 'capacidade' não pode estar vazio")

        @Min(value = 1, message = "A capacidade deve ser maior que zero")
        int capacidade,

        @NotNull(message = "O campo 'localização' não pode estar vazio")
        String localizacao
) {}
