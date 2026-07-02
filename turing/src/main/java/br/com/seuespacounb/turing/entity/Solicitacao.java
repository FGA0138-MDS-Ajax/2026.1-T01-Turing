package br.com.seuespacounb.turing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String motivo;

    @Column(name = "quantidade_participantes", nullable = false)
    private Integer quantidadeParticipantes;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_uso", nullable = false)
    private LocalDate dataUso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    @Column(name = "observacao_adm")
    private String observacaoAdm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_sala_id", nullable = false)
    private HorarioSala horarioSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario solicitante;
}