package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="jogador")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Jogador implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_jogador")
    private Long idJogador;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    @Column(name="pontuacao")
    private int pontuacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time", updatable = false, nullable = false)
    private Time time;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;
}
