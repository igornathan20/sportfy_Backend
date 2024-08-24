package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="modalidade_esportiva")
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_modalidade_esportiva")
    @Setter @Getter
    private Long idModalidadeEsportiva;

    @Column(name="nome", nullable = false)
    @Setter @Getter
    private String nome;

    @Column(name="descricao", nullable = false)
    @Setter @Getter
    private String descricao;

    @Column(name="foto")
    @Setter @Getter
    private String foto;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataCriacao;

    @Column(name="ativo", insertable = false)
    @Setter @Getter
    private boolean ativo;
}
