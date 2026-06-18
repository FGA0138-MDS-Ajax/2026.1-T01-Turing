package br.com.seuespacounb.turing.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.seuespacounb.turing.dto.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.SalaResponseDTO;
import br.com.seuespacounb.turing.entity.Sala;

@Mapper(componentModel = "spring")
public interface SalaMapper {
    Sala toEntity(SalaRequestDTO dto);
    SalaResponseDTO toResponseDTO(Sala entity);
    List<SalaResponseDTO> toListResponseDTO(List<Sala> entities);
}