package br.com.seuespacounb.turing.entity;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String cpf;
    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipoUsuario == TipoUsuario.ADM) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADM"),
                    new SimpleGrantedAuthority("ROLE_CLIENTE")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;
    }
}
