package br.com.seuespacounb.turing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "horario_sala")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HorarioSala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inicio_periodo",nullable = false)
    private LocalDate inicioPeriodo;

    @Column(name = "fim_periodo",nullable = false)
    private LocalDate fimPeriodo;

    @Column(name = "dia_semana",nullable = false)
    private DayOfWeek diaSemana;

    @Column(name = "inicio_hora",nullable = false)
    private LocalTime inicioHora;

    @Column(name = "fim_hora",nullable = false)
    private LocalTime fimHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusHorario status;

    @Column(name = "descricao_ocupacao", nullable = false)
    private String descricaoOcupacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
}