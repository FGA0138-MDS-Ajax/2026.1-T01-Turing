package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.StatusHorario;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T23:02:21-0300",
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

        horarioSala.inicioPeriodo( dto.inicioPeriodo() );
        horarioSala.fimPeriodo( dto.fimPeriodo() );
        horarioSala.diaSemana( dto.diaSemana() );
        horarioSala.inicioHora( dto.inicioHora() );
        horarioSala.fimHora( dto.fimHora() );
        if ( dto.status() != null ) {
            horarioSala.status( Enum.valueOf( StatusHorario.class, dto.status() ) );
        }
        horarioSala.descricaoOcupacao( dto.descricaoOcupacao() );

        return horarioSala.build();
    }

    @Override
    public HorarioSalaResponseDTO paraHorarioResponseDTO(HorarioSala entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        LocalDate inicioPeriodo = null;
        LocalDate fimPeriodo = null;
        DayOfWeek diaSemana = null;
        LocalTime inicioHora = null;
        LocalTime fimHora = null;
        String status = null;
        String descricaoOcupacao = null;

        id = entity.getId();
        inicioPeriodo = entity.getInicioPeriodo();
        fimPeriodo = entity.getFimPeriodo();
        diaSemana = entity.getDiaSemana();
        inicioHora = entity.getInicioHora();
        fimHora = entity.getFimHora();
        if ( entity.getStatus() != null ) {
            status = entity.getStatus().name();
        }
        descricaoOcupacao = entity.getDescricaoOcupacao();

        HorarioSalaResponseDTO horarioSalaResponseDTO = new HorarioSalaResponseDTO( id, inicioPeriodo, fimPeriodo, diaSemana, inicioHora, fimHora, status, descricaoOcupacao );

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
}
