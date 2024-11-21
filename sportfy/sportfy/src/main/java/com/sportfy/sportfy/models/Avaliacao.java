package com.sportfy.sportfy.models;

import com.sportfy.sportfy.dtos.AvaliacaoJogadorDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="avaliacao")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_avaliacao")
    private Long idAvaliacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico_avaliado", updatable = false, nullable = false)
    private Academico academicoAvaliado;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    @Column(name="nota", updatable = false, nullable = false)
    private int nota;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico_avaliador", updatable = false, nullable = false)
    private Academico avaliador;

    public AvaliacaoJogadorDto toDto(Avaliacao avaliacao) {
        return new AvaliacaoJogadorDto(
                avaliacao.getIdAvaliacao(),
                avaliacao.getAcademicoAvaliado().getIdAcademico(),
                avaliacao.getModalidadeEsportiva().getIdModalidadeEsportiva(),
                avaliacao.getNota(),
                avaliacao.getAvaliador() != null ? avaliacao.getAvaliador().getIdAcademico() : null
        );
    }
}