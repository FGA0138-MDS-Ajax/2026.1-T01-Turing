package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.config.JWTUserData;
import br.com.seuespacounb.turing.dto.request.FiltroSolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.request.JustificaticaSolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.request.SolicitacaoRequestDTO;
import br.com.seuespacounb.turing.dto.response.SolicitacaoResponseDTO;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.service.SolicitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitacao")
@RequiredArgsConstructor

public class SolicitacaoController {
    private final SolicitacaoService solicitacaoService;

    @PostMapping
    public ResponseEntity<SolicitacaoResponseDTO> salvar(@RequestBody @Valid SolicitacaoRequestDTO solicitacaoRequestDTO) throws ConflictException, NotFoundException {
        SolicitacaoResponseDTO novaSolicitacao =  solicitacaoService.salvarSolicitacao(solicitacaoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSolicitacao);
    }

    @GetMapping("usuario/filtroOrdenacao")
    public ResponseEntity<Page<SolicitacaoResponseDTO>> listarSolicitacaoParaUsuario(
            @AuthenticationPrincipal JWTUserData usuarioLogado,
            @ModelAttribute FiltroSolicitacaoRequestDTO filtro,
            @RequestParam(defaultValue = "0")int pagina,
            @RequestParam(defaultValue = "10")int tamanho,
            @RequestParam(defaultValue = "dataSolicitacao")String ordenacao,
            @RequestParam(defaultValue = "desc")String direcao
    ) throws NotFoundException{
        Page<SolicitacaoResponseDTO> salas = solicitacaoService.filtrarParaUsuario(filtro, usuarioLogado.userId(), pagina,tamanho,ordenacao, direcao);
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/adm/filtroOrdenacao")
    public ResponseEntity<Page<SolicitacaoResponseDTO>> listarTodasSolicitacao(
            @ModelAttribute FiltroSolicitacaoRequestDTO filtro,
            @RequestParam(defaultValue = "0")int pagina,
            @RequestParam(defaultValue = "10")int tamanho,
            @RequestParam(defaultValue = "dataSolicitacao")String ordenacao,
            @RequestParam(defaultValue = "asc")String direcao
    ){
        Page<SolicitacaoResponseDTO> salas = solicitacaoService.filtrarParaAdm(filtro, pagina,tamanho,ordenacao, direcao);
        return ResponseEntity.ok(salas);
    }

    @PatchMapping("/{id}/aprovacao")
    public ResponseEntity<SolicitacaoResponseDTO> aprovarSolicitacao(
            @PathVariable Long id) throws NotFoundException {
        SolicitacaoResponseDTO solicitacaoAtualizada = solicitacaoService.aprovarSolicitacao(id);
        return ResponseEntity.ok(solicitacaoAtualizada);
    }

    @PatchMapping("/{id}/rejeicao")
    public ResponseEntity<SolicitacaoResponseDTO> rejeitarSolicitacao(
            @PathVariable Long id,
            @RequestBody @Valid JustificaticaSolicitacaoRequestDTO justificaticao) throws NotFoundException {
        SolicitacaoResponseDTO solicitacaoAtualizada = solicitacaoService.rejeitarSolicitacao(id, justificaticao);
        return ResponseEntity.ok(solicitacaoAtualizada);
    }

    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<SolicitacaoResponseDTO> cancelarSolicitacao(
            @PathVariable Long id,
            @RequestBody @Valid JustificaticaSolicitacaoRequestDTO justificaticao) throws NotFoundException {
        SolicitacaoResponseDTO solicitacaoAtualizada = solicitacaoService.cancelarSolicitacao(id, justificaticao);
        return ResponseEntity.ok(solicitacaoAtualizada);
    }
}