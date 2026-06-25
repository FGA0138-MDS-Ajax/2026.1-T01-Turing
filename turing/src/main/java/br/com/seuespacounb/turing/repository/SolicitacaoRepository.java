package br.com.seuespacounb.turing.repository;

import br.com.seuespacounb.turing.entity.Solicitacao;
import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    // lista solicitações ativas (dataUso >= hoje) de uma sala
    @Query("SELECT s FROM Solicitacao s WHERE s.horarioSala.sala.id = :salaId AND s.dataUso >= :hoje")
    List<Solicitacao> findAtivasPorSala(
            @Param("salaId") Long salaId,
            @Param("hoje") LocalDate hoje);

    List<Solicitacao> findBySolicitanteId(Long solicitanteId);

    // verifica conflito: mesmo horário, mesma data, status PENDENTE ou APROVADA
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Solicitacao s " +
            "WHERE s.horarioSala.id = :horarioSalaId " +
            "AND s.dataUso = :dataUso " +
            "AND s.status IN :statusAtivos " +
            "AND s.id <> :ignorarId")
    boolean existeConflito(
            @Param("horarioSalaId") Long horarioSalaId,
            @Param("dataUso") LocalDate dataUso,
            @Param("statusAtivos") List<StatusSolicitacao> statusAtivos,
            @Param("ignorarId") Long ignorarId);

    @Query("""
    SELECT s FROM Solicitacao s
    WHERE s.horarioSala.id = :horarioSalaId
      AND s.dataUso = :dataUso
      AND s.status = 'PENDENTE'
      AND s.id <> :excludeId
""")
    List<Solicitacao> findConcorrentesPendentes(
            @Param("horarioSalaId") Long horarioSalaId,
            @Param("dataUso") LocalDate dataUso,
            @Param("excludeId") Long excludeId);

    boolean existsByHorarioSalaId(Long horarioSalaId);
}