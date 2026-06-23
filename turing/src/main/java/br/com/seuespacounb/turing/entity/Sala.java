package br.com.seuespacounb.turing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sala", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "localizacao"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "horarios")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private String localizacao;
}