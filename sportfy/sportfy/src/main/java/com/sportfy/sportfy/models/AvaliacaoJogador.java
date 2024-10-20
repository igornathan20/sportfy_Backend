package com.sportfy.sportfy.models;

import com.sportfy.sportfy.dtos.AvaliacaoJogadorDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="avaliacao")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoJogador {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_avaliacao")
    private Long idAvaliacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_jogador", updatable = false, nullable = false)
    private Jogador jogador;

    @Column(name="nota", updatable = false, nullable = false)
    private int nota;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico_avaliador", updatable = false, nullable = false)
    private Academico avaliador;

    public AvaliacaoJogadorDto toDto(AvaliacaoJogador avaliacao) {
        return new AvaliacaoJogadorDto(
                avaliacao.getIdAvaliacao(),
                avaliacao.getJogador().getIdJogador(),
                avaliacao.getNota(),
                avaliacao.getAvaliador() != null ? avaliacao.getAvaliador().getIdAcademico() : null
        );
    }
}