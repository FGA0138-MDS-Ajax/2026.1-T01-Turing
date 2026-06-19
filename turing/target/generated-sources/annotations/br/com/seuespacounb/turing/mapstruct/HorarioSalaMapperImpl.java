package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-19T00:54:18-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class HorarioSalaMapperImpl implements HorarioSalaMapper {

    @Override
    public HorarioSala paraHorarioSala(HorarioSalaRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HorarioSala.HorarioSalaBuilder horarioSala = HorarioSala.builder();

        horarioSala.diaSemana( dto.diaSemana() );
        horarioSala.inicioHora( dto.inicioHora() );
        horarioSala.fimHora( dto.fimHora() );
        horarioSala.descricaoOcupacao( dto.descricaoOcupacao() );

        return horarioSala.build();
    }

    @Override
    public HorarioSalaResponseDTO paraHorarioResponseDTO(HorarioSala entity) {
        if ( entity == null ) {
            return null;
        }

        Long salaId = null;
        Long id = null;
        DayOfWeek diaSemana = null;
        LocalTime inicioHora = null;
        LocalTime fimHora = null;
        String descricaoOcupacao = null;

        salaId = entitySalaId( entity );
        id = entity.getId();
        diaSemana = entity.getDiaSemana();
        inicioHora = entity.getInicioHora();
        fimHora = entity.getFimHora();
        descricaoOcupacao = entity.getDescricaoOcupacao();

        HorarioSalaResponseDTO horarioSalaResponseDTO = new HorarioSalaResponseDTO( id, diaSemana, inicioHora, fimHora, descricaoOcupacao, salaId );

        return horarioSalaResponseDTO;
    }

    @Override
    public List<HorarioSalaResponseDTO> paraListaHorarioResponseDTO(List<HorarioSala> entities) {
        if ( entities == null ) {
            return null;
        }

        List<HorarioSalaResponseDTO> list = new ArrayList<HorarioSalaResponseDTO>( entities.size() );
        for ( HorarioSala horarioSala : entities ) {
            list.add( paraHorarioResponseDTO( horarioSala ) );
        }

        return list;
    }

    private Long entitySalaId(HorarioSala horarioSala) {
        if ( horarioSala == null ) {
            return null;
        }
        Sala sala = horarioSala.getSala();
        if ( sala == null ) {
            return null;
        }
        Long id = sala.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
