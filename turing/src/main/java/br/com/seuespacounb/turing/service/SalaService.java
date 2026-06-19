package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.FiltroSalaRequest;
import br.com.seuespacounb.turing.dto.request.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.SalaResponseDTO;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.exception.*;
import br.com.seuespacounb.turing.mapstruct.SalaMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
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

    private final HorarioSalaRepository horarioSalaRepository;
    private final SalaRepository repository;
    private final SalaMapper mapper;

    public SalaResponseDTO salvarSala(SalaRequestDTO requestDTO) throws ConflictException {
        if (repository.existsByNomeAndLocalizacao(requestDTO.nome(), requestDTO.localizacao())) {
            throw new ConflictException("Já existe uma sala com o nome '"
                    + requestDTO.nome() + "' na localização '" + requestDTO.localizacao() + "'");
        }

        Sala novaSala = mapper.toEntity(requestDTO);
        return mapper.toResponseDTO(repository.saveAndFlush(novaSala));
    }


    public SalaResponseDTO buscarSalaPorId(Long id) throws NotFoundException, MethodArgumentTypeMismatchException {
        Sala sala = buscarOuLancarErro(id);
        return mapper.toResponseDTO(sala);
    }

    public List<SalaResponseDTO> listarSalas(){
        return mapper.toListResponseDTO(repository.findAll());
    }

    public SalaResponseDTO atualizarSala(Long id, SalaRequestDTO requestDTO) throws NotFoundException, MethodArgumentTypeMismatchException {
        Sala salaExistente = buscarOuLancarErro(id);

        salaExistente.setNome(requestDTO.nome());
        salaExistente.setCapacidade(requestDTO.capacidade());
        salaExistente.setLocalizacao(requestDTO.localizacao());

        return mapper.toResponseDTO(repository.saveAndFlush(salaExistente));
    }

    public void deletarSala(Long id) throws NotFoundException, MethodArgumentTypeMismatchException, ConflictException {
        buscarOuLancarErro(id);

        if (horarioSalaRepository.existsBySalaId(id)) {
            throw new ConflictException(
                    "Não é possível excluir esta sala pois existem horários cadastrados para ela."
            );
        }

        repository.deleteById(id);
    }

    public List<SalaResponseDTO> filtrarPorNome(String nome) throws NotFoundException, MissingServletRequestParameterException {
        if (repository.findByNomeContainingIgnoreCase(nome).isEmpty()) {
            throw new NotFoundException("Nenhuma sala encontrada com esse nome");
        }

        return mapper.toListResponseDTO(repository.findByNomeContainingIgnoreCase(nome));
    }

    public Page<SalaResponseDTO> filtrarOrdenar(
            FiltroSalaRequest filtro,
            int pagina,
            int tamanho,
            String ordenacao,
            String direcao
    ) throws BadRequestException {

        Specification<Sala> spec = Specification
                .where(SalaSpecifications.possuiNome(filtro.nome()))
                .and(SalaSpecifications.possuiCapacidade(filtro.capacidade()))
                .and(SalaSpecifications.possuiLocalizacao(filtro.localizacao()))
                .and(SalaSpecifications.possuiDiaSemana(filtro.diaSemana()))
                .and(SalaSpecifications.possuiInicioHora(filtro.inicioHora()))
                .and(SalaSpecifications.possuiFimHora(filtro.fimHora()))
                .and(SalaSpecifications.disponivelEm(filtro.dataUso()));

        List<String> camposPermitidos = List.of("nome", "capacidade", "localizacao");
        if(!camposPermitidos.contains(ordenacao)){
            ordenacao = "nome";
        }

        Sort sort = direcao != null && direcao.equalsIgnoreCase("desc")
                ? Sort.by(ordenacao).descending()
                : Sort.by(ordenacao).ascending();

        if (tamanho <= 0) {
            throw new BadRequestException("O tamanho da página deve ser maior que zero.");
        }

        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        Page<Sala> filtradosOrdenados = repository.findAll(spec, pageable);
        return filtradosOrdenados.map(mapper::toResponseDTO);
    }

    public Sala buscarOuLancarErro(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sala não encontrada com id: " + id));
    }
}