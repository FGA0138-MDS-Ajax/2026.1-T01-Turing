package br.com.seuespacounb.turing.specification;

import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.entity.StatusHorario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.time.DayOfWeek;
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

    public static Specification<Sala> possuiStatus(StatusHorario status) {
        return (root, query, cb) -> {
            if (status == null) return cb.conjunction();
            Join<Sala, HorarioSala> horarioJoin = root.join("horarios");
            return cb.equal(horarioJoin.get("status"), status);
        };
    }
}