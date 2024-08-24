package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="meta_diaria")
@NoArgsConstructor
@AllArgsConstructor
public class MetaDiaria implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_meta_diaria")
    @Setter @Getter
    private Long idMetaDiaria;

    @Column(name="titulo", nullable = false)
    @Setter @Getter
    private String titulo;

    @Column(name="objetivo", nullable = false)
    @Setter @Getter
    private String objetivo;

    @Column(name="quantidade_concluido", insertable = false)
    @Setter @Getter
    private int quantidadeConcluido;

    @Column(name="progresso_atual", insertable = false)
    @Setter @Getter
    private int progressoAtual;

    @Column(name="progresso_maximo", nullable = false)
    @Setter @Getter
    private int progressoMaximo;

    @Column(name="progresso_item", nullable = false)
    @Setter @Getter
    private String progressoItem;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_situacao_meta_diaria", nullable = false)
    @Setter @Getter
    private SituacaoMetaDiaria situacaoMetaDiaria;
}
