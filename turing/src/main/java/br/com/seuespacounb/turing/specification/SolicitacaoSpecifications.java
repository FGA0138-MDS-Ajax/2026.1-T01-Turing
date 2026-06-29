package br.com.seuespacounb.turing.specification;

import br.com.seuespacounb.turing.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class SolicitacaoSpecifications {
    public static Specification<Solicitacao> possuiDataSolicitacao(LocalDateTime dataSolicitacao){
        return (root, query, cb) ->{
            if(dataSolicitacao == null) return cb.conjunction();
            return cb.equal(root.get("dataSolicitacao"), dataSolicitacao);
        };
    }

    public static Specification<Solicitacao> possuiStatus(StatusSolicitacao status){
        return (root, query, cb) ->{
            if(status == null) return cb.conjunction();
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Solicitacao> possuiDiaSemana(DayOfWeek diaSemana){
        return (root, query, cb) ->{
            if(diaSemana == null) return cb.conjunction();
            Join<Solicitacao, HorarioSala> horarioJoin = root.join("horarioSala");
            return cb.equal(horarioJoin.get("diaSemana"), diaSemana);
        };
    }

    public static Specification<Solicitacao> possuiNomeSala(String nomeSala){
        return (root, query, cb) ->{
            if(nomeSala == null) return cb.conjunction();
            Join<Solicitacao, HorarioSala> horarioJoin = root.join("horarioSala");
            Join<HorarioSala, Sala> salaJoin = horarioJoin.join("sala");
            return cb.equal(salaJoin.get("nome"), nomeSala);
        };
    }

    public static Specification<Solicitacao> possuiUserId(Long userId){
        return (root, query, cb) ->{
            if(userId == null) return cb.conjunction();
            Join<Solicitacao, Usuario> horarioJoin = root.join("usuario");
            return cb.equal(horarioJoin.get("id"), userId);
        };
    }
}
