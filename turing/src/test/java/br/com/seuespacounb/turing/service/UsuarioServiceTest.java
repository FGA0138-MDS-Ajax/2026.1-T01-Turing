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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenConfig tokenConfig;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioCliente;
    private Usuario usuarioAdm;

    @BeforeEach
    void setUp() {
        usuarioCliente = new Usuario();
        usuarioCliente.setId(1L);
        usuarioCliente.setName("Rafaela Teste");
        usuarioCliente.setEmail("teste@gmail.com");
        usuarioCliente.setCpf("111.111.111-11");
        usuarioCliente.setPassword("senha_encoded");
        usuarioCliente.setTipoUsuario(TipoUsuario.CLIENTE);

        usuarioAdm = new Usuario();
        usuarioAdm.setId(2L);
        usuarioAdm.setName("Admin Teste");
        usuarioAdm.setEmail("admin@gmail.com");
        usuarioAdm.setCpf("222.222.222-22");
        usuarioAdm.setPassword("admin_encoded");
        usuarioAdm.setTipoUsuario(TipoUsuario.ADM);
    }

    // CT-04 — Validação de login / autenticação do usuário
    @Test
    @DisplayName("CT-04 | Alterar dados do próprio usuário — deve salvar e retornar novo token")
    void alterarDadosProprioUsuario_dadosValidos_deveRetornarNovoToken() throws NotFoundException {
        AtualizarUsuarioRequestDTO dados = new AtualizarUsuarioRequestDTO(
                "Rafaela Novo Nome", null, null, null, null);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioCliente));
        when(usuarioRepository.save(any())).thenReturn(usuarioCliente);
        when(tokenConfig.generateToken(any())).thenReturn("novo_token_jwt");

        AtualizarUsuarioResponseDTO resultado = usuarioService.alterarDadosProprioUsuario(1L, dados);

        assertNotNull(resultado);
        assertEquals("novo_token_jwt", resultado.token());
        verify(usuarioRepository).save(usuarioCliente);
    }

    @Test
    @DisplayName("CT-04 | Alterar dados de usuário inexistente — deve lançar NotFoundException")
    void alterarDadosProprioUsuario_usuarioInexistente_deveLancarNotFoundException() {
        AtualizarUsuarioRequestDTO dados = new AtualizarUsuarioRequestDTO(
                "Nome", null, null, null, null);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> usuarioService.alterarDadosProprioUsuario(99L, dados));
    }

    @Test
    @DisplayName("CT-04 | Alterar senha — deve encodar a senha antes de salvar")
    void alterarSenha_deveEncodarSenhaAntesDeSlavar() throws NotFoundException {
        AtualizarUsuarioRequestDTO dados = new AtualizarUsuarioRequestDTO(
                null, null, null, "novaSenha123", null);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioCliente));
        when(passwordEncoder.encode("novaSenha123")).thenReturn("senha_nova_encoded");
        when(usuarioRepository.save(any())).thenReturn(usuarioCliente);
        when(tokenConfig.generateToken(any())).thenReturn("token");

        usuarioService.alterarDadosProprioUsuario(1L, dados);

        verify(passwordEncoder).encode("novaSenha123");
        assertEquals("senha_nova_encoded", usuarioCliente.getPassword());
    }

    @Test
    @DisplayName("Deletar próprio usuário — deve remover do repositório")
    void deletarDadosProprioUsuario_usuarioExistente_deveRemover() throws NotFoundException {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioCliente));

        assertDoesNotThrow(() -> usuarioService.deletarDadosProprioUsuario(1L));

        verify(usuarioRepository).delete(usuarioCliente);
    }

    @Test
    @DisplayName("Deletar usuário inexistente — deve lançar NotFoundException")
    void deletarDadosProprioUsuario_usuarioInexistente_deveLancarNotFoundException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> usuarioService.deletarDadosProprioUsuario(99L));
    }

    @Test
    @DisplayName("Admin listar usuários — deve retornar lista")
    void getUsuarios_comAdm_deveRetornarLista() throws NotFoundException {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioAdm));
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioCliente, usuarioAdm));

        List<AdmGetUsuarioDTO> resultado = usuarioService.getUsuarios(2L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Cliente tentar listar usuários — deve lançar AccessDeniedException")
    void getUsuarios_comCliente_deveLancarAccessDeniedException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioCliente));

        assertThrows(AccessDeniedException.class,
                () -> usuarioService.getUsuarios(1L));
    }

    @Test
    @DisplayName("Admin deletar outro admin — deve lançar UnauthorizedException")
    void admDeletarUsuario_tentarDeletarAdm_deveLancarUnauthorizedException() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioAdm));
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(usuarioAdm));

        assertThrows(UnauthorizedException.class,
                () -> usuarioService.admDeletarUsuario(2L, 3L));
    }

    @Test
    @DisplayName("Admin encontrar usuário por email — deve retornar usuário")
    void admEncontrarUsuarioPorEmail_emailValido_deveRetornarUsuario() throws NotFoundException {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioAdm));
        when(usuarioRepository.findUserByEmail("teste@gmail.com"))
                .thenReturn(Optional.of(usuarioCliente));

        AdmGetUsuarioDTO resultado = usuarioService.admEncontrarUsuarioPorEmail(2L, "teste@gmail.com");

        assertNotNull(resultado);
        assertEquals("teste@gmail.com", resultado.email());
    }

    @Test
    @DisplayName("Admin encontrar usuário por email inexistente — deve lançar NotFoundException")
    void admEncontrarUsuarioPorEmail_emailInexistente_deveLancarNotFoundException() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioAdm));
        when(usuarioRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> usuarioService.admEncontrarUsuarioPorEmail(2L, "naoexiste@gmail.com"));
    }
}
