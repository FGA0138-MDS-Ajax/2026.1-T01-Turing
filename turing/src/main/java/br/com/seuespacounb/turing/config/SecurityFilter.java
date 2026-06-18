package br.com.seuespacounb.turing.config;

import br.com.seuespacounb.turing.entity.Usuario;
import br.com.seuespacounb.turing.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizedHeader = request.getHeader("Authorization");

        if(Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")){

            String token = authorizedHeader.substring("Bearer ".length());
            Optional<JWTUserData> optUser = tokenConfig.validateToken(token);

            if (optUser.isPresent()) {

                JWTUserData userData = optUser.get();
                Usuario usuario = usuarioRepository.findByEmail(userData.email());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
        else{
            filterChain.doFilter(request, response);
        }
    }
}
