package br.com.seuespacounb.turing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horario_sala")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class HorarioSala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DayOfWeek diaSemana;

    @Column(name = "inicio_hora", nullable = false)
    private LocalTime inicioHora;

    @Column(name = "fim_hora", nullable = false)
    private LocalTime fimHora;

    @Column(name = "descricao_ocupacao")
    private String descricaoOcupacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
}