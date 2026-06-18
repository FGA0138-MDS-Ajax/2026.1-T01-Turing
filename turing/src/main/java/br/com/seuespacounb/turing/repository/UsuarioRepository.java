package br.com.seuespacounb.turing.repository;

import br.com.seuespacounb.turing.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUserByEmail(String email);
    Usuario findByEmail(String email);
}
