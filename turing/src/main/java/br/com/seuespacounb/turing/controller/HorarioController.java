package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.service.HorarioSalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turing")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioSalaService horarioSalaService;

    @GetMapping("/horarios/sala/{salaId}")
    public ResponseEntity<List<HorarioSalaResponseDTO>> buscarHorariosPorSala(
            @PathVariable Long salaId) throws NotFoundException {
        return ResponseEntity.ok(horarioSalaService.listarHorariosPorSala(salaId));
    }

    @GetMapping("/horarios/{id}")
    public ResponseEntity<HorarioSalaResponseDTO> buscarPorId(
            @PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(horarioSalaService.buscarPorId(id));
    }

    @PostMapping("/horarios")
    public ResponseEntity<HorarioSalaResponseDTO> salvar(
            @Valid @RequestBody HorarioSalaRequestDTO dto) throws NotFoundException, ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioSalaService.salvarHorario(dto));
    }

    @DeleteMapping("/horarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) throws NotFoundException, ConflictException {
        horarioSalaService.excluirHorario(id);
    }
}