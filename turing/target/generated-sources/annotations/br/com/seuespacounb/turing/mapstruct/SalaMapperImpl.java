package br.com.seuespacounb.turing.mapstruct;

import br.com.seuespacounb.turing.dto.request.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.SalaResponseDTO;
import br.com.seuespacounb.turing.entity.Sala;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-22T21:52:43-0300",
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

        id = entity.getId();
        nome = entity.getNome();
        capacidade = entity.getCapacidade();
        localizacao = entity.getLocalizacao();

        SalaResponseDTO salaResponseDTO = new SalaResponseDTO( id, nome, capacidade, localizacao );

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
}
