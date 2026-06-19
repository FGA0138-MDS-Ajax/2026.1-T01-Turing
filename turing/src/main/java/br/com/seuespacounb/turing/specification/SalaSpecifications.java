package br.com.seuespacounb.turing.specification;

import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.entity.Solicitacao;
import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class SalaSpecifications {
    public static Specification<Sala> possuiNome(String nome) {
        return (root, query, cb) -> {
            if (nome == null) return cb.conjunction();
            return cb.equal(root.get("nome"), nome);
        };
    }

    public static Specification<Sala> possuiCapacidade(Integer capacidade) {
        return (root, query, cb) -> {
            if (capacidade == null) return cb.conjunction();
            return cb.equal(root.get("capacidade"), capacidade);
        };
    }

    public static Specification<Sala> possuiLocalizacao(String localizacao) {
        return (root, query, cb) -> {
            if (localizacao == null) return cb.conjunction();
            return cb.equal(root.get("localizacao"), localizacao);
        };
    }

    public static Specification<Sala> possuiDiaSemana(DayOfWeek diaSemana) {
        return (root, query, cb) -> {
            if (diaSemana == null) return cb.conjunction();
            Join<Sala, HorarioSala> horarioJoin = root.join("horarios");
            return cb.equal(horarioJoin.get("diaSemana"), diaSemana);
        };
    }

    public static Specification<Sala> possuiInicioHora(LocalTime inicioHora) {
        return (root, query, cb) -> {
            if (inicioHora == null) return cb.conjunction();
            Join<Sala, HorarioSala> horarioJoin = root.join("horarios");
            return cb.equal(horarioJoin.get("inicioHora"), inicioHora);
        };
    }

    public static Specification<Sala> possuiFimHora(LocalTime fimHora) {
        return (root, query, cb) -> {
            if (fimHora == null) return cb.conjunction();
            Join<Sala, HorarioSala> horarioJoin = root.join("horarios");
            return cb.equal(horarioJoin.get("fimHora"), fimHora);
        };
    }

    // Substitui possuiStatus. Só filtra se dataUso for informada —
    // sem data não dá pra responder "disponível quando?".
    public static Specification<Sala> disponivelEm(LocalDate dataUso) {
        return (root, query, cb) -> {
            if (dataUso == null) return cb.conjunction();

            Join<Sala, HorarioSala> horarioJoin = root.join("horarios");

            Subquery<Long> subquery = query.subquery(Long.class);
            var solicitacaoRoot = subquery.from(Solicitacao.class);
            subquery.select(solicitacaoRoot.get("id"));
            subquery.where(
                    cb.equal(solicitacaoRoot.get("horarioSala"), horarioJoin),
                    cb.equal(solicitacaoRoot.get("dataUso"), dataUso),
                    solicitacaoRoot.get("status").in(StatusSolicitacao.PENDENTE, StatusSolicitacao.APROVADA)
            );

            return cb.not(cb.exists(subquery));
        };
    }
}