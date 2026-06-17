package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.request.FiltroSalaRequest;
import br.com.seuespacounb.turing.dto.request.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.SalaResponseDTO;
import br.com.seuespacounb.turing.exception.BadRequestException;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.MethodArgumentNotValidException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.seuespacounb.turing.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turing")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService service;

    // GET /salas                          → lista todas
    // GET /salas?nome=Sala+A              → filtra por nome
    // GET /salas?diaSemana=MONDAY         → filtra por dia da semana
    // GET /salas?status=VAGO&diaSemana=FRIDAY → combinação de filtros

    @PostMapping("/salas")
    public ResponseEntity<SalaResponseDTO> salvarSala(@Valid @RequestBody SalaRequestDTO requestDTO) throws ConflictException, MethodArgumentNotValidException, BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarSala(requestDTO));
    }

    @GetMapping("/salas")
    public ResponseEntity<List<SalaResponseDTO>> listarSalas(){
        return ResponseEntity.ok(service.listarSalas());
    }

    @GetMapping("/salas/{id}")
    public ResponseEntity<SalaResponseDTO> buscarSalaPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarSalaPorId(id));
    }

    @PutMapping("/salas/{id}")
    public ResponseEntity<SalaResponseDTO> atualizarSala(
            @PathVariable Long id,
            @Valid @RequestBody SalaRequestDTO requestDTO){

        return ResponseEntity.ok(service.atualizarSala(id, requestDTO));
    }

    @DeleteMapping("/salas/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id){
        service.deletarSala(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salas/filtrar")
        public ResponseEntity<List<SalaResponseDTO>> filtrarPorNome(@RequestParam String nome){
        return ResponseEntity.ok(service.filtrarPorNome(nome));
}

    @GetMapping("/salas/filtroOrdenacao")
    public ResponseEntity<Page<SalaResponseDTO>> pesquisarHorario(
            @ModelAttribute FiltroSalaRequest filtro,
            @RequestParam(defaultValue = "0")int pagina,
            @RequestParam(defaultValue = "10")int tamanho,
            @RequestParam(defaultValue = "nome")String ordenacao,
            @RequestParam(defaultValue = "asc")String direcao
    ){
        Page<SalaResponseDTO> salas = service.filtrarOrdenar(filtro, pagina,tamanho,ordenacao, direcao);
        return ResponseEntity.ok(salas);
    }
}