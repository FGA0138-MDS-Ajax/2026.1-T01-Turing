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


                        .requestMatchers(HttpMethod.PUT, "/usuarios").hasAnyRole("CLIENTE", "ADM")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios").hasAnyRole("CLIENTE", "ADM")


                        .requestMatchers(HttpMethod.GET, "/usuarios/adm").hasRole("ADM")
                        .requestMatchers(HttpMethod.GET, "/usuarios/adm/encontrarPorEmail").hasRole("ADM")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/adm/{idUsuarioParaAlterar}").hasRole("ADM")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/adm/{idUsuarioParaDeletar}").hasRole("ADM")


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
