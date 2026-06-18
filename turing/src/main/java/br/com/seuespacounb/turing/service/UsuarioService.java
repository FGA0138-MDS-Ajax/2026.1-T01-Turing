package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.config.TokenConfig;
import br.com.seuespacounb.turing.dto.AdmGetUsuarioDTO;
import br.com.seuespacounb.turing.dto.UsuarioDTO;
import br.com.seuespacounb.turing.dto.request.AtualizarUsuarioRequestDTO;
import br.com.seuespacounb.turing.dto.response.AtualizarUsuarioResponseDTO;
import br.com.seuespacounb.turing.entity.TipoUsuario;
import br.com.seuespacounb.turing.entity.Usuario;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.exception.UnauthorizedException;
import br.com.seuespacounb.turing.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;

    public AtualizarUsuarioResponseDTO alterarDadosProprioUsuario(Long idUsuarioLogado, AtualizarUsuarioRequestDTO dados) throws NotFoundException {

        Usuario usuario = usuarioRepository.findById(idUsuarioLogado)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (dados.name() != null) {
            usuario.setName(dados.name());
        }

        if (dados.email() != null) {
            usuario.setEmail(dados.email());
        }

        if (dados.cpf() != null) {
            usuario.setCpf(dados.cpf());
        }

        if (dados.senha() != null) {
            usuario.setSenha(passwordEncoder.encode(dados.senha()));
        }

        if (dados.tipoUsuario() != null) {
            usuario.setTipoUsuario(dados.tipoUsuario());
        }

        usuarioRepository.save(usuario);

        String novoToken = tokenConfig.generateToken(usuario);

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getName(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getSenha(),
                usuario.getTipoUsuario()
        );

        return new AtualizarUsuarioResponseDTO(usuarioDTO, novoToken);
    }

    public void deletarDadosProprioUsuario(Long idUsuarioLogado) throws NotFoundException {
        Usuario usuario = usuarioRepository.findById(idUsuarioLogado)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }






    public List<AdmGetUsuarioDTO> getUsuarios(Long idAdm) throws NotFoundException {
        Usuario usuarioAdm = usuarioRepository.findById(idAdm)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        if (usuarioAdm.getTipoUsuario() != TipoUsuario.ADM) {
            throw new AccessDeniedException("Usuário não é um administrador");
        }

        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new AdmGetUsuarioDTO(
                        usuario.getId(),
                        usuario.getName(),
                        usuario.getEmail(),
                        usuario.getCpf(),
                        usuario.getTipoUsuario()
                ))
                .toList();
    }

    public void admAlterarDadosUsuario(Long idAdm, Long idUsuarioPraAlterar, UsuarioDTO usuarioDTO) throws NotFoundException {
        Usuario usuarioAdm = usuarioRepository.findById(idAdm)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        Usuario usuarioCliente = usuarioRepository.findById(idUsuarioPraAlterar)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        if (usuarioAdm.getTipoUsuario() != TipoUsuario.ADM) {
            throw new AccessDeniedException("Usuário não é um administrador");
        }

        if (usuarioDTO.name() != null){
            usuarioCliente.setName(usuarioDTO.name());
        }

        if (usuarioDTO.email() != null){
            usuarioCliente.setEmail(usuarioDTO.email());
        }

        if (usuarioDTO.cpf() != null){
            usuarioCliente.setCpf(usuarioDTO.cpf());
        }

        if (usuarioDTO.tipoUsuario() != null) {
            usuarioCliente.setTipoUsuario(usuarioDTO.tipoUsuario());
        }

        usuarioRepository.save(usuarioCliente);
    }

    public void admDeletarUsuario(Long idAdm, Long idUsuarioDeletado) throws NotFoundException, UnauthorizedException {
        Usuario usuarioAdm = usuarioRepository.findById(idAdm)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        Usuario usuarioCliente = usuarioRepository.findById(idUsuarioDeletado)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        if (usuarioAdm.getTipoUsuario() != TipoUsuario.ADM) {
            throw new AccessDeniedException("Usuário não é um administrador");
        }

        if (usuarioCliente.getTipoUsuario() == TipoUsuario.ADM) {
            throw new UnauthorizedException("Não pode deletar um administrador");
        }


        usuarioRepository.delete(usuarioCliente);
    }

    public AdmGetUsuarioDTO admEncontrarUsuarioPorEmail(Long idAdm, String email) throws NotFoundException {

        Usuario usuarioAdm = usuarioRepository.findById(idAdm)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        if (usuarioAdm.getTipoUsuario() != TipoUsuario.ADM) {
            throw new AccessDeniedException("Usuário não é um administrador");
        }

        Usuario usuarioCliente = usuarioRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        return new AdmGetUsuarioDTO(
                usuarioCliente.getId(),
                usuarioCliente.getName(),
                usuarioCliente.getEmail(),
                usuarioCliente.getCpf(),
                usuarioCliente.getTipoUsuario()
        );
    }
}
