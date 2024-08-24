package com.sportfy.sportfy.models;

import java.io.Serializable;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="apoio_saude")
@NoArgsConstructor
@AllArgsConstructor
public class ApoioSaude implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_apoio_saude")
    @Setter @Getter
    private Long idApoioSaude;

    @Column(name="nome", nullable = false)
    @Setter @Getter
    private String nome;

    @Column(name="email")
    @Setter @Getter
    private String email;

    @Column(name="telefone")
    @Setter @Getter
    private String telefone;

    @Column(name="descricao", nullable = false)
    @Setter @Getter
    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_publicacao", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataPublicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_administrador", updatable = false, nullable = false)
    @Setter @Getter
    private Administrador administrador;
}
