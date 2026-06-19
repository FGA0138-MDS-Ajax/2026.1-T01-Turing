package br.com.seuespacounb.turing.config;

import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize->authorize

                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/turing/salas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/turing/salas").hasRole("ADM")
                        .requestMatchers(HttpMethod.PUT, "/turing/salas/**").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/salas/**").hasRole("ADM")

                        .requestMatchers(HttpMethod.GET, "/turing/horarios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/turing/horarios").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/horarios/**").hasRole("ADM")

                        .requestMatchers(HttpMethod.GET, "/turing/solicitacoes/sala/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/turing/solicitacoes/minhas").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.GET, "/turing/solicitacoes/**").hasAnyRole("CLIENTE", "ADM")

                        .requestMatchers(HttpMethod.POST, "/turing/solicitacoes").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.PATCH, "/turing/solicitacoes/*/cancelar").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.PATCH, "/turing/solicitacoes/*/status").hasRole("ADM")

                        .requestMatchers(HttpMethod.PUT, "/turing/usuarios").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/usuarios").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.GET, "/turing/usuarios/adm").hasRole("ADM")
                        .requestMatchers(HttpMethod.GET, "/turing/usuarios/adm/encontrarPorEmail").hasRole("ADM")
                        .requestMatchers(HttpMethod.PUT, "/turing/usuarios/adm/{idUsuarioParaAlterar}").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/usuarios/adm/{idUsuarioParaDeletar}").hasRole("ADM")

                        .anyRequest().authenticated())

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
