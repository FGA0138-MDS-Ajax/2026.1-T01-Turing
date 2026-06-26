package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.mapstruct.HorarioSalaMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SalaRepository;
import br.com.seuespacounb.turing.repository.SolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioSalaServiceTest {

    @Mock private HorarioSalaRepository horarioRepository;
    @Mock private SalaRepository salaRepository;
    @Mock private SolicitacaoRepository solicitacaoRepository;
    @Mock private HorarioSalaMapper mapper;
    @InjectMocks private HorarioSalaService horarioSalaService;

    private HorarioSala horario;
    private HorarioSalaRequestDTO requestDTO;
    private HorarioSalaResponseDTO responseDTO;
    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala();
        sala.setId(1L);
        sala.setNome("Sala Teste");

        horario = new HorarioSala();
        horario.setId(1L);
        horario.setDiaSemana(DayOfWeek.MONDAY);
        horario.setInicioHora(LocalTime.of(8, 0));
        horario.setFimHora(LocalTime.of(10, 0));
        horario.setSala(sala);

        requestDTO = new HorarioSalaRequestDTO(
                DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0), null, 1L);

        responseDTO = new HorarioSalaResponseDTO(
                1L, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0), null, 1L);
    }

    @Test @DisplayName("Salvar horario sem conflito")
    void salvarHorario_semConflito_deveRetornar() throws Exception {
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        when(horarioRepository.existeConflito(any(), any(), any(), any(), any())).thenReturn(false);
        when(mapper.paraHorarioSala(requestDTO)).thenReturn(horario);
        when(horarioRepository.saveAndFlush(horario)).thenReturn(horario);
        when(mapper.paraHorarioResponseDTO(horario)).thenReturn(responseDTO);
        assertNotNull(horarioSalaService.salvarHorario(requestDTO));
    }

    @Test @DisplayName("Salvar horario com conflito")
    void salvarHorario_comConflito_deveLancarConflictException() {
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        when(horarioRepository.existeConflito(any(), any(), any(), any(), any())).thenReturn(true);
        assertThrows(ConflictException.class, () -> horarioSalaService.salvarHorario(requestDTO));
    }

    @Test @DisplayName("Salvar horario com sala inexistente")
    void salvarHorario_salaInexistente_deveLancarNotFoundException() {
        when(salaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> horarioSalaService.salvarHorario(requestDTO));
    }

    @Test @DisplayName("Listar horarios por sala existente")
    void listarHorariosPorSala_existente_deveRetornarLista() throws Exception {
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        when(horarioRepository.findBySalaId(1L)).thenReturn(List.of(horario));
        when(mapper.paraListaHorarioResponseDTO(any())).thenReturn(List.of(responseDTO));
        assertFalse(horarioSalaService.listarHorariosPorSala(1L).isEmpty());
    }

    @Test @DisplayName("Listar horarios por sala inexistente")
    void listarHorariosPorSala_inexistente_deveLancarNotFoundException() {
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> horarioSalaService.listarHorariosPorSala(99L));
    }

    @Test @DisplayName("Listar todos os horarios")
    void listarTodosHorarios_deveRetornarLista() {
        when(horarioRepository.findAll()).thenReturn(List.of(horario));
        when(mapper.paraListaHorarioResponseDTO(any())).thenReturn(List.of(responseDTO));
        assertFalse(horarioSalaService.listarTodosHorarios().isEmpty());
    }

    @Test @DisplayName("Buscar horario por id existente")
    void buscarPorId_existente_deveRetornar() throws Exception {
        when(horarioRepository.findById(1L)).thenReturn(Optional.of(horario));
        when(mapper.paraHorarioResponseDTO(horario)).thenReturn(responseDTO);
        assertNotNull(horarioSalaService.buscarPorId(1L));
    }

    @Test @DisplayName("Buscar horario por id inexistente")
    void buscarPorId_inexistente_deveLancarNotFoundException() {
        when(horarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> horarioSalaService.buscarPorId(99L));
    }

    @Test @DisplayName("Excluir horario sem solicitacoes")
    void excluirHorario_semSolicitacoes_deveExcluir() throws Exception {
        when(horarioRepository.findById(1L)).thenReturn(Optional.of(horario));
        when(solicitacaoRepository.existsByHorarioSalaId(1L)).thenReturn(false);
        assertDoesNotThrow(() -> horarioSalaService.excluirHorario(1L));
        verify(horarioRepository).delete(horario);
    }

    @Test @DisplayName("Excluir horario com solicitacoes vinculadas")
    void excluirHorario_comSolicitacoes_deveLancarConflictException() {
        when(horarioRepository.findById(1L)).thenReturn(Optional.of(horario));
        when(solicitacaoRepository.existsByHorarioSalaId(1L)).thenReturn(true);
        assertThrows(ConflictException.class, () -> horarioSalaService.excluirHorario(1L));
    }

    @Test @DisplayName("Excluir horario inexistente")
    void excluirHorario_inexistente_deveLancarNotFoundException() {
        when(horarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> horarioSalaService.excluirHorario(99L));
    }
}