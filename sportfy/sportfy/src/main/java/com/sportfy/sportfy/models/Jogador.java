package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.List;

import com.sportfy.sportfy.dtos.JogadorDto;
import com.sportfy.sportfy.enums.TipoSituacaoJogador;
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

    @Column(name="situacao_jogador")
    private TipoSituacaoJogador situacaoJogador = TipoSituacaoJogador.EM_ABERTO;

    @Column(name="pontuacao")
    private int pontuacao;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "avaliacao",
            joinColumns = @JoinColumn(name = "id_avaliacao"),
            inverseJoinColumns = @JoinColumn(name = "id_academico_avaliado")
    )
    private List<AvaliacaoJogador> avaliacoes;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_time", updatable = false, nullable = false)
    private Time time;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;

    public JogadorDto toDto(Jogador jogador) {
        return new JogadorDto(
                jogador.getIdJogador(),
                jogador.getModalidadeEsportiva().getIdModalidadeEsportiva(),
                jogador.getSituacaoJogador(),
                jogador.getPontuacao(),
                jogador.getTime().getIdTime(),
                jogador.getAcademico().getIdAcademico()
        );
    }
}
