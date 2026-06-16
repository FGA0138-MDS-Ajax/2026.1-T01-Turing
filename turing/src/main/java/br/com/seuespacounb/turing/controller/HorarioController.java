package br.com.seuespacounb.turing.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.seuespacounb.turing.dto.AtualizarStatusRequest;
import br.com.seuespacounb.turing.dto.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.service.HorarioSalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor

public class HorarioController {

    private final HorarioSalaService horarioSalaService;

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<HorarioSalaResponseDTO>> buscarPorSala(@PathVariable Long salaId) {
        List<HorarioSalaResponseDTO> horarios = horarioSalaService.listarHorariosPorSala(salaId);
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<HorarioSalaResponseDTO>> buscarTodosHorarios() {
        List<HorarioSalaResponseDTO> horarios = horarioSalaService.listarTodosHorarios();
        return ResponseEntity.ok(horarios);
    }

    @PostMapping
    public ResponseEntity<HorarioSalaResponseDTO> salvar(@RequestBody @Valid HorarioSalaRequestDTO horarioSalaRequestDTO) throws ConflictException, NotFoundException{
        HorarioSalaResponseDTO horarioSalvo =  horarioSalaService.salvarHorario(horarioSalaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(
            @PathVariable Long id,
            @RequestParam LocalDate inicioPeriodo,
            @RequestParam DayOfWeek diaSemana,
            @RequestParam LocalTime inicioHora) throws NotFoundException {
        horarioSalaService.excluirHorarioPorSala(id, inicioPeriodo, diaSemana, inicioHora);
        return ResponseEntity.ok("Horário removido");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<HorarioSalaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusRequest request) throws NotFoundException {

        HorarioSalaResponseDTO horarioAtualizado = horarioSalaService.atualizarStatusHorario(id, request.status());

        return ResponseEntity.ok(horarioAtualizado);
    }
}
