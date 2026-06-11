package br.com.seuespacounb.turing.mapstruct;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;

@Mapper(componentModel = "spring")
public interface HorarioSalaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sala", ignore = true)
    HorarioSala paraHorarioSala(HorarioSalaRequestDTO dto);

    @Mapping(source = "sala.id", target = "salaId")
    HorarioSalaResponseDTO paraHorarioResponseDTO(HorarioSala entity);

    List<HorarioSalaResponseDTO> paraListaHorarioResponseDTO(List<HorarioSala> entities);
}