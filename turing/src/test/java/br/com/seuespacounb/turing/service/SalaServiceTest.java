package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.SalaResponseDTO;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.mapstruct.SalaMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock private SalaRepository repository;
    @Mock private HorarioSalaRepository horarioSalaRepository;
    @Mock private SalaMapper mapper;
    @InjectMocks private SalaService salaService;

    private Sala sala;
    private SalaRequestDTO requestDTO;
    private SalaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        sala = new Sala();
        sala.setId(1L);
        sala.setNome("Sala Teste");
        sala.setCapacidade(30);
        sala.setLocalizacao("Bloco Teste");

        requestDTO = new SalaRequestDTO("Sala Teste", 30, "Bloco Teste");
        responseDTO = new SalaResponseDTO(1L, "Sala Teste", 30, "Bloco Teste");
    }

    @Test @DisplayName("Salvar sala sem conflito")
    void salvarSala_semConflito_deveRetornarSalaCriada() throws Exception {
        when(repository.existsByNomeAndLocalizacao(any(), any())).thenReturn(false);
        when(mapper.toEntity(requestDTO)).thenReturn(sala);
        when(repository.saveAndFlush(sala)).thenReturn(sala);
        when(mapper.toResponseDTO(sala)).thenReturn(responseDTO);
        assertNotNull(salaService.salvarSala(requestDTO));
    }

    @Test @DisplayName("Salvar sala com nome e localizacao duplicados")
    void salvarSala_comConflito_deveLancarConflictException() {
        when(repository.existsByNomeAndLocalizacao(any(), any())).thenReturn(true);
        assertThrows(ConflictException.class, () -> salaService.salvarSala(requestDTO));
    }

    @Test @DisplayName("Buscar sala por id existente")
    void buscarSalaPorId_existente_deveRetornar() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sala));
        when(mapper.toResponseDTO(sala)).thenReturn(responseDTO);
        assertNotNull(salaService.buscarSalaPorId(1L));
    }

    @Test @DisplayName("Buscar sala por id inexistente")
    void buscarSalaPorId_inexistente_deveLancarNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> salaService.buscarSalaPorId(99L));
    }

    @Test @DisplayName("Listar salas")
    void listarSalas_deveRetornarLista() {
        when(repository.findAll()).thenReturn(List.of(sala));
        when(mapper.toListResponseDTO(any())).thenReturn(List.of(responseDTO));
        assertFalse(salaService.listarSalas().isEmpty());
    }

    @Test @DisplayName("Atualizar sala existente")
    void atualizarSala_existente_deveRetornarAtualizada() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sala));
        when(repository.saveAndFlush(sala)).thenReturn(sala);
        when(mapper.toResponseDTO(sala)).thenReturn(responseDTO);
        assertNotNull(salaService.atualizarSala(1L, requestDTO));
    }

    @Test @DisplayName("Atualizar sala inexistente")
    void atualizarSala_inexistente_deveLancarNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> salaService.atualizarSala(99L, requestDTO));
    }

    @Test @DisplayName("Deletar sala sem horarios vinculados")
    void deletarSala_semHorarios_deveExcluir() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sala));
        when(horarioSalaRepository.existsBySalaId(1L)).thenReturn(false);
        assertDoesNotThrow(() -> salaService.deletarSala(1L));
        verify(repository).deleteById(1L);
    }

    @Test @DisplayName("Deletar sala com horarios vinculados")
    void deletarSala_comHorarios_deveLancarConflictException() {
        when(repository.findById(1L)).thenReturn(Optional.of(sala));
        when(horarioSalaRepository.existsBySalaId(1L)).thenReturn(true);
        assertThrows(ConflictException.class, () -> salaService.deletarSala(1L));
    }

    @Test @DisplayName("Deletar sala inexistente")
    void deletarSala_inexistente_deveLancarNotFoundException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> salaService.deletarSala(99L));
    }
}