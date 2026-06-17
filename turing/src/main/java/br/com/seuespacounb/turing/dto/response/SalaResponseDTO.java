package br.com.seuespacounb.turing.dto.response;

public record SalaResponseDTO(
        Long id,
        String nome,
        int capacidade,
        String localizacao
) {}
