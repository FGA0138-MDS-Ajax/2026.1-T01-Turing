package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.request.AtualizarStatusSolicitacaoRequest;
import br.com.seuespacounb.turing.dto.request.SolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.entity.Usuario;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.exception.UnauthorizedException;
import br.com.seuespacounb.turing.service.SolicitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/turing")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    @PostMapping("/solicitacoes")
    public ResponseEntity<SolicitacaoResponseDTO> criar(
            @Valid @RequestBody SolicitacaoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) throws NotFoundException, ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitacaoService.criarSolicitacao(dto, usuarioLogado));
    }

    @GetMapping("/solicitacoes/sala/{salaId}")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarPorSala(
            @PathVariable Long salaId) {
        return ResponseEntity.ok(solicitacaoService.listarSolicitacoesPorSala(salaId));
    }

    @GetMapping("/solicitacoes/minhas")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarMinhas(
            @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(solicitacaoService.listarMinhasSolicitacoes(usuarioLogado.getId()));
    }

    @GetMapping("/solicitacoes/{id}")
    public ResponseEntity<SolicitacaoResponseDTO> buscarPorId(
            @PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(solicitacaoService.buscarPorId(id));
    }

    @PatchMapping("/solicitacoes/{id}/status")
    public ResponseEntity<SolicitacaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusSolicitacaoRequest request) throws NotFoundException {
        return ResponseEntity.ok(solicitacaoService.atualizarStatus(id, request));
    }

    @PatchMapping("/solicitacoes/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado)
            throws NotFoundException, UnauthorizedException, ConflictException {
        solicitacaoService.cancelarSolicitacao(id, usuarioLogado);
    }
}