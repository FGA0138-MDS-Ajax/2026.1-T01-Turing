package br.com.seuespacounb.turing.config;

import br.com.seuespacounb.turing.entity.TipoUsuario;
import br.com.seuespacounb.turing.entity.Usuario;
import br.com.seuespacounb.turing.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdmSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.seed.email:adm@gmail.com}")
    private String admEmail;

    @Value("${admin.seed.senha:123}")
    private String admSenha;

    @Value("${admin.seed.cpf:00000000000}")
    private String admCpf;

    @Override
    public void run(String... args) {
        boolean existeAdm = usuarioRepository.existsByTipoUsuario(TipoUsuario.ADM);

        if (!existeAdm) {
            Usuario admin = new Usuario();
            admin.setName("Administrador");
            admin.setEmail(admEmail);
            admin.setCpf(admCpf);
            admin.setPassword(passwordEncoder.encode(admSenha));
            admin.setTipoUsuario(TipoUsuario.ADM);

            usuarioRepository.save(admin);
            System.out.println("[AdmSeeder] Nenhum ADM encontrado. ADM padrão criado: " + admEmail);
        } else {
            System.out.println("[AdmSeeder] Já existe ADM no sistema. Nada a fazer.");
        }
    }
}