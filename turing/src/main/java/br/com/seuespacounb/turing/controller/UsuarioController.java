package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.AdmGetUsuarioDTO;
import br.com.seuespacounb.turing.dto.UsuarioDTO;
import br.com.seuespacounb.turing.dto.request.AtualizarUsuarioRequestDTO;
import br.com.seuespacounb.turing.dto.response.AtualizarUsuarioResponseDTO;
import br.com.seuespacounb.turing.entity.Usuario;
import br.com.seuespacounb.turing.exception.MethodNotAllowedException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.exception.UnauthorizedException;
import br.com.seuespacounb.turing.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turing")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PutMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarUsuarioResponseDTO alterarDadosUsuario(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestBody AtualizarUsuarioRequestDTO dados
    ) throws NotFoundException, HttpRequestMethodNotSupportedException {

       return usuarioService.alterarDadosProprioUsuario(usuarioLogado.getId(), dados);
    }

    @DeleteMapping("/usuarios")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirDadosProprioUsuario(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) throws NotFoundException, HttpRequestMethodNotSupportedException {

        usuarioService.deletarDadosProprioUsuario(usuarioLogado.getId());
    }

    @GetMapping("/usuarios/me")
    @ResponseStatus(HttpStatus.OK)
    public AdmGetUsuarioDTO getMeuPerfil(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) throws NotFoundException {

        return usuarioService.getMeuPerfil(usuarioLogado.getId());
    }


    @GetMapping("/usuarios/adm")
    @ResponseStatus(HttpStatus.OK)
    public List<AdmGetUsuarioDTO> admGetUsuarios(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) throws NotFoundException, HttpRequestMethodNotSupportedException, MethodNotAllowedException {

        return usuarioService.getUsuarios(usuarioLogado.getId());
    }

    @PutMapping("/usuarios/adm/{idUsuarioParaAlterar}")
    @ResponseStatus(HttpStatus.OK)
    public void admAlterarDadosUsuario(
            @PathVariable Long idUsuarioParaAlterar,
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestBody UsuarioDTO usuarioDTO
    )throws NotFoundException, HttpRequestMethodNotSupportedException {

        usuarioService.admAlterarDadosUsuario(
                usuarioLogado.getId(),
                idUsuarioParaAlterar,
                usuarioDTO
        );
    }

    @DeleteMapping("/usuarios/adm/{idUsuarioParaDeletar}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void admDeletarUsuario(
            @PathVariable Long idUsuarioParaDeletar,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) throws NotFoundException, UnauthorizedException, HttpRequestMethodNotSupportedException {
        usuarioService.admDeletarUsuario(usuarioLogado.getId(), idUsuarioParaDeletar);
    }

    @GetMapping("/usuarios/adm/encontrarPorEmail")
    @ResponseStatus(HttpStatus.OK)
    public AdmGetUsuarioDTO admEncontrarUsuarioPorEmail(
            @RequestParam String email,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) throws NotFoundException, HttpRequestMethodNotSupportedException {
        return usuarioService.admEncontrarUsuarioPorEmail(usuarioLogado.getId(), email);
    }
}
