package br.com.seuespacounb.turing.repository;


import br.com.seuespacounb.turing.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, JpaSpecificationExecutor<Solicitacao> {

    @Query("SELECT COUNT(solicitacao)>0 FROM Solicitacao solicitacao " +
            "WHERE solicitacao.dataSolicitacao <= :dataLimite " +
            "AND solicitacao.horario.id = :horarioId")
    boolean conflitoSolicitacao(
            @Param("dataLimite") LocalDateTime dataLimite,
            @Param("horarioId") Long horarioId
    );
}