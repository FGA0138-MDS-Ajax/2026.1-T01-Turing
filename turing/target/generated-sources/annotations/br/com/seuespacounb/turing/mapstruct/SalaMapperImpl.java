package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.dto.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.SalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
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
public class SalaMapperImpl implements SalaMapper {

    @Override
    public Sala toEntity(SalaRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Sala.SalaBuilder sala = Sala.builder();

        sala.nome( dto.nome() );
        sala.capacidade( dto.capacidade() );
        sala.localizacao( dto.localizacao() );

        return sala.build();
    }

    @Override
    public SalaResponseDTO toResponseDTO(Sala entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        int capacidade = 0;
        String localizacao = null;
        List<HorarioSalaResponseDTO> horarios = null;

        id = entity.getId();
        nome = entity.getNome();
        capacidade = entity.getCapacidade();
        localizacao = entity.getLocalizacao();
        horarios = horarioSalaListToHorarioSalaResponseDTOList( entity.getHorarios() );

        SalaResponseDTO salaResponseDTO = new SalaResponseDTO( id, nome, capacidade, localizacao, horarios );

        return salaResponseDTO;
    }

    @Override
    public List<SalaResponseDTO> toListResponseDTO(List<Sala> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SalaResponseDTO> list = new ArrayList<SalaResponseDTO>( entities.size() );
        for ( Sala sala : entities ) {
            list.add( toResponseDTO( sala ) );
        }

        return list;
    }

    protected HorarioSalaResponseDTO horarioSalaToHorarioSalaResponseDTO(HorarioSala horarioSala) {
        if ( horarioSala == null ) {
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

        id = horarioSala.getId();
        inicioPeriodo = horarioSala.getInicioPeriodo();
        fimPeriodo = horarioSala.getFimPeriodo();
        diaSemana = horarioSala.getDiaSemana();
        inicioHora = horarioSala.getInicioHora();
        fimHora = horarioSala.getFimHora();
        if ( horarioSala.getStatus() != null ) {
            status = horarioSala.getStatus().name();
        }
        descricaoOcupacao = horarioSala.getDescricaoOcupacao();

        HorarioSalaResponseDTO horarioSalaResponseDTO = new HorarioSalaResponseDTO( id, inicioPeriodo, fimPeriodo, diaSemana, inicioHora, fimHora, status, descricaoOcupacao );

        return horarioSalaResponseDTO;
    }

    protected List<HorarioSalaResponseDTO> horarioSalaListToHorarioSalaResponseDTOList(List<HorarioSala> list) {
        if ( list == null ) {
            return null;
        }

        List<HorarioSalaResponseDTO> list1 = new ArrayList<HorarioSalaResponseDTO>( list.size() );
        for ( HorarioSala horarioSala : list ) {
            list1.add( horarioSalaToHorarioSalaResponseDTO( horarioSala ) );
        }

        return list1;
    }
}
