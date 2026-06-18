package br.com.seuespacounb.turing.dto;

import java.util.List;

public record SalaResponseDTO(
        Long id,
        String nome,
        int capacidade,
        String localizacao,
        List<HorarioSalaResponseDTO> horarios
) {}
