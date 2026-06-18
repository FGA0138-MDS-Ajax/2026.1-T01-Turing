package br.com.seuespacounb.turing.repository;

import br.com.seuespacounb.turing.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>, JpaSpecificationExecutor<Sala> {
    Optional<Sala> findByNome(String nome);
    List<Sala> findByNomeContainingIgnoreCase(String nome);
}