package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.FiltroSalaRequest;
import br.com.seuespacounb.turing.dto.SalaRequestDTO;
import br.com.seuespacounb.turing.dto.SalaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.seuespacounb.turing.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService service;

    @PostMapping
    public ResponseEntity<SalaResponseDTO> salvarSala(@RequestBody SalaRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarSala(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listarSalas(){
        return ResponseEntity.ok(service.listarSalas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarSalaPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarSalaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> atualizarSala(
            @PathVariable Long id,
            @RequestBody SalaRequestDTO requestDTO){

        return ResponseEntity.ok(service.atualizarSala(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id){
        service.deletarSala(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
        public ResponseEntity<List<SalaResponseDTO>> filtrarPorNome(@RequestParam String nome){
    return ResponseEntity.ok(service.filtrarPorNome(nome));
}

    @GetMapping("/filtroOrdenacao")
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