package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="jogador")
@NoArgsConstructor
@AllArgsConstructor
public class Jogador implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_jogador")
    @Setter @Getter
    private Long idJogador;

    @Column(name="pontuacao", insertable = false)
    @Setter @Getter
    private int pontuacao;

    @Column(name="melhor_jogador", insertable = false)
    @Setter @Getter
    private int melhorJogador;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time_partida", updatable = false, nullable = false)
    @Setter @Getter
    private TimePartida timePartida;
}
