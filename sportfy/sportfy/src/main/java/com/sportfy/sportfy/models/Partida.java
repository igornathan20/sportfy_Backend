package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name="partida")
@NoArgsConstructor
@AllArgsConstructor
public class Partida implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_partida")
    @Setter @Getter
    private Long idPartida;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_partida", nullable = false)
    @Setter @Getter
    private OffsetDateTime dataPartida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato", updatable = false, nullable = false)
    @Setter @Getter
    private Campeonato campeonato;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_fase_partida", updatable = false, nullable = false)
    @Setter @Getter
    private FasePartida fasePartida;
}
