package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="conquista")
@NoArgsConstructor
@AllArgsConstructor
public class Conquista implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_conquista")
    @Setter @Getter
    private Long idConquista;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_conquista", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataConquista;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_meta_esportiva", updatable = false, nullable = false)
    @Setter @Getter
    private MetaEsportiva metaEsportiva;
}
