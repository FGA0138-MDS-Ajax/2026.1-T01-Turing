package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.config.TokenConfig;
import br.com.seuespacounb.turing.dto.request.LoginRequest;
import br.com.seuespacounb.turing.dto.request.RegisterUserRequest;
import br.com.seuespacounb.turing.dto.response.LoginResponse;
import br.com.seuespacounb.turing.dto.response.RegisterUserResponse;
import br.com.seuespacounb.turing.entity.Usuario;
import br.com.seuespacounb.turing.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.generateToken(usuario);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        Usuario novoUsuario = new Usuario();

        novoUsuario.setName(request.name());
        novoUsuario.setEmail(request.email());
        novoUsuario.setCpf(request.cpf());
        novoUsuario.setSenha(passwordEncoder.encode(request.password()));
        novoUsuario.setTipoUsuario(request.tipoUsuario());

        usuarioRepository.save(novoUsuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterUserResponse(novoUsuario.getName(), novoUsuario.getEmail()));
    }
}
