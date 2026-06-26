package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
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
import br.com.seuespacounb.turing.exception.HttpMessageNotReadableException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.service.HorarioSalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/turing")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioSalaService horarioSalaService;

    @GetMapping("/horarios/sala/{salaId}")
    public ResponseEntity<List<HorarioSalaResponseDTO>> buscarHorarioPorSala(
            @PathVariable Long salaId) throws NotFoundException, HttpRequestMethodNotSupportedException {
        return ResponseEntity.ok(horarioSalaService.listarHorariosPorSala(salaId));
    }

    @GetMapping("/horarios/{id}")
    public ResponseEntity<HorarioSalaResponseDTO> buscarPorId(
            @PathVariable Long id) throws NotFoundException, HttpRequestMethodNotSupportedException {
        return ResponseEntity.ok(horarioSalaService.buscarPorId(id));
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

    @PostMapping("/horarios")
    public ResponseEntity<HorarioSalaResponseDTO> salvar(
            @Valid @RequestBody HorarioSalaRequestDTO dto) throws NotFoundException, ConflictException, HttpMessageNotReadableException, HttpRequestMethodNotSupportedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioSalaService.salvarHorario(dto));
    }

    @DeleteMapping("/horarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) throws NotFoundException, ConflictException, HttpRequestMethodNotSupportedException {
        horarioSalaService.excluirHorario(id);
    }
}