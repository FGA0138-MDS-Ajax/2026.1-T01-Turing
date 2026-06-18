package br.com.seuespacounb.turing.mapstruct;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.com.seuespacounb.turing.dto.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;

@Mapper(componentModel = "spring")
public interface HorarioSalaMapper {
    @Mapping(target = "id", ignore = true)
    HorarioSala paraHorarioSala(HorarioSalaRequestDTO dto);
    HorarioSalaResponseDTO paraHorarioResponseDTO(HorarioSala entity);
    List<HorarioSalaResponseDTO> paraListaHorarioResponseDTO(List<HorarioSala> entities);
}