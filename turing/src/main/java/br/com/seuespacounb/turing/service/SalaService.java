package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.FiltroSalaRequest;
import br.com.seuespacounb.turing.dto.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.SalaResponseDTO;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.mapstruct.SalaMapper;
import br.com.seuespacounb.turing.repository.SalaRepository;
import br.com.seuespacounb.turing.specification.SalaSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository repository;
    private final SalaMapper mapper; 

    public SalaResponseDTO salvarSala(SalaRequestDTO requestDTO){
        Sala novaSala = mapper.toEntity(requestDTO);
        return mapper.toResponseDTO(repository.saveAndFlush(novaSala));
    }

    public SalaResponseDTO buscarSalaPorId(Long id){
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        return mapper.toResponseDTO(sala);
    }

    public List<SalaResponseDTO> listarSalas(){
        return mapper.toListResponseDTO(repository.findAll());
    }

    public SalaResponseDTO atualizarSala(Long id, SalaRequestDTO requestDTO){
        Sala salaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        salaExistente.setNome(requestDTO.nome());
        salaExistente.setCapacidade(requestDTO.capacidade());
        salaExistente.setLocalizacao(requestDTO.localizacao());

        return mapper.toResponseDTO(repository.saveAndFlush(salaExistente));
    }

    public void deletarSala(Long id){
        repository.deleteById(id);
    }

    public List<SalaResponseDTO> filtrarPorNome(String nome){
        return mapper.toListResponseDTO(repository.findByNomeContainingIgnoreCase(nome));
    }

    public Page<SalaResponseDTO> filtrarOrdenar(FiltroSalaRequest filtro, int pagina, int tamanho, String ordenacao, String direcao){
        Specification<Sala> spec = Specification
                .where(SalaSpecifications.possuiNome(filtro.nome()))
                .and(SalaSpecifications.possuiCapacidade(filtro.capacidade()))
                .and(SalaSpecifications.possuiLocalizacao(filtro.localizacao()))
                .and(SalaSpecifications.possuiDiaSemana(filtro.diaSemana()))
                .and(SalaSpecifications.possuiInicioHora(filtro.inicioHora()))
                .and(SalaSpecifications.possuiFimHora(filtro.fimHora()))
                .and(SalaSpecifications.possuiStatus(filtro.status()));
        List<String> camposPermitidos = List.of("nome", "capacidade", "localizacao");
        if(!camposPermitidos.contains(ordenacao)){
            ordenacao = "nome";
        }
        Sort sort = direcao != null && direcao.equalsIgnoreCase("desc")
                ? Sort.by(ordenacao).descending():
                Sort.by(ordenacao).ascending();
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        Page<Sala> filtradosOrdenados = repository.findAll(spec, pageable);
        return filtradosOrdenados.map(mapper::toResponseDTO);
    }
}