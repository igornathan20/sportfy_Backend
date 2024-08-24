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
@Table(name="curtida_comentario")
@NoArgsConstructor
@AllArgsConstructor
public class CurtidaComentario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_curtida_comentario")
    @Setter @Getter
    private Long idCurtidaComentario;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_curtida", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataCurtida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_comentario", updatable = false, nullable = false)
    @Setter @Getter
    private Comentario comentario;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    @Setter @Getter
    private Usuario usuario;
}
