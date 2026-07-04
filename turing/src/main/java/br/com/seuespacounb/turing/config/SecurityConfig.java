package br.com.seuespacounb.turing.config;

import br.com.seuespacounb.turing.handler.CustomAccessDeniedHandler;
import br.com.seuespacounb.turing.handler.CustomAuthenticationEntryPoint;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(authorize->authorize

                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                        .requestMatchers(HttpMethod.PUT, "/turing/usuarios").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/usuarios").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.GET, "/turing/usuarios/adm").hasRole("ADM")
                        .requestMatchers(HttpMethod.GET, "/turing/usuarios/adm/encontrarPorEmail").hasRole("ADM")
                        .requestMatchers(HttpMethod.PUT, "/turing/usuarios/adm/{idUsuarioParaAlterar}").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/usuarios/adm/{idUsuarioParaDeletar}").hasRole("ADM")


                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/swagger-ui/index.html").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/turing/salas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/turing/salas").hasRole("ADM")
                        .requestMatchers(HttpMethod.PUT, "/turing/salas/**").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/salas/**").hasRole("ADM")


                        .requestMatchers(HttpMethod.GET, "/turing/horarios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/turing/horarios/sala/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/turing/horarios").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/turing/horarios/**").hasRole("ADM")


                        .requestMatchers(HttpMethod.GET, "/turing/solicitacoes/sala/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/turing/solicitacoes/minhas").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.GET, "/turing/solicitacoes/**").hasAnyRole("CLIENTE", "ADM")

                        .requestMatchers(HttpMethod.POST, "/turing/solicitacoes").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.PATCH, "/turing/solicitacoes/*/cancelar").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.PATCH, "/turing/solicitacoes/*/status").hasRole("ADM")

                        .requestMatchers(HttpMethod.GET, "/agenda/evento/*").hasAnyRole("CLIENTE", "ADM")

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
