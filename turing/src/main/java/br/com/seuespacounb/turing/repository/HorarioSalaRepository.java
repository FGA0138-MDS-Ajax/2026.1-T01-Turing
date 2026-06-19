package br.com.seuespacounb.turing.repository;

import br.com.seuespacounb.turing.entity.HorarioSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioSalaRepository extends JpaRepository<HorarioSala, Long> {

    List<HorarioSala> findBySalaId(Long salaId);

    // verifica se já existe outro horário fixo nessa sala/dia/horário
    // ignorarId = -1L no create, id real no update
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END FROM HorarioSala h " +
            "WHERE h.sala.id = :salaId " +
            "AND h.diaSemana = :diaSemana " +
            "AND h.inicioHora < :fimHora " +
            "AND h.fimHora > :inicioHora " +
            "AND h.id <> :ignorarId")
    boolean existeConflito(
            @Param("salaId") Long salaId,
            @Param("diaSemana") DayOfWeek diaSemana,
            @Param("inicioHora") LocalTime inicioHora,
            @Param("fimHora") LocalTime fimHora,
            @Param("ignorarId") Long ignorarId);

    boolean existsBySalaId(Long id);
}