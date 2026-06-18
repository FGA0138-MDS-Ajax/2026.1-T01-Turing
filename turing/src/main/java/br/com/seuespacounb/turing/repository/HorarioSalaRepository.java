package br.com.seuespacounb.turing.repository;

import br.com.seuespacounb.turing.entity.HorarioSala;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioSalaRepository extends JpaRepository<HorarioSala, Long>{

    List<HorarioSala> findBySalaId(Long salaId);

    boolean existsBySalaIdAndInicioPeriodoAndDiaSemanaAndInicioHora(
            Long salaId, LocalDate inicioPeriodo,
            DayOfWeek diaSemana, LocalTime inicioHora);

    void deleteBySalaIdAndInicioPeriodoAndDiaSemanaAndInicioHora(
            Long salaId, LocalDate inicioPeriodo,
            DayOfWeek diaSemana, LocalTime inicioHora);

    @Query("SELECT COUNT(horario)>0 FROM HorarioSala horario " +
            "WHERE horario.sala.id= :salaId " +
            "AND horario.inicioPeriodo <= :fimPeriodo " +
            "AND horario.fimPeriodo >= :inicioPeriodo " +
            "AND horario.diaSemana = :diaSemana " +
            "AND horario.inicioHora <= :fimHora " +
            "AND horario.fimHora >= :inicioHora ")
    boolean conflito(
            @Param("salaId") Long salaId,
            @Param("fimPeriodo") LocalDate fimPeriodo,
            @Param("inicioPeriodo") LocalDate inicioPeriodo,
            @Param("diaSemana") DayOfWeek diaSemana,
            @Param("fimHora") LocalTime fimHora,
            @Param("inicioHora") LocalTime inicioHora);
}