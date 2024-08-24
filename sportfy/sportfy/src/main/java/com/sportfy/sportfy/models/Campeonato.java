package com.sportfy.sportfy.models;

import java.io.Serializable;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name="campeonato")
@NoArgsConstructor
@AllArgsConstructor
public class Campeonato implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_campeonato")
    @Setter @Getter
    private Long idCampeonato;

    @Column(name="codigo", unique = true)
    @Setter @Getter
    private String codigo;

    @Column(name="titulo", nullable = false)
    @Setter @Getter
    private String titulo;

    @Column(name="descricao", nullable = false)
    @Setter @Getter
    private String descricao;

    @Column(name="aposta")
    @Setter @Getter
    private String aposta;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_inicio", nullable = false)
    @Setter @Getter
    private OffsetDateTime dataInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_fim", nullable = false)
    @Setter @Getter
    private OffsetDateTime dataFim;

    @Column(name="limite_times", nullable = false)
    @Setter @Getter
    private int limiteTimes;

    @Column(name="limite_participantes", nullable = false)
    @Setter @Getter
    private int limiteParticipantes;

    @Column(name="ativo", insertable = false)
    @Setter @Getter
    private boolean ativo;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_endereco", nullable = false)
    @Setter @Getter
    private Endereco endereco;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_privacidade_campeonato", nullable = false)
    @Setter @Getter
    private PrivacidadeCampeonato privacidadeCampeonato;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    @Setter @Getter
    private ModalidadeEsportiva modalidadeEsportiva;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_situacao_campeonato", nullable = false)
    @Setter @Getter
    private SituacaoCampeonato situacaoCampeonato;
}
