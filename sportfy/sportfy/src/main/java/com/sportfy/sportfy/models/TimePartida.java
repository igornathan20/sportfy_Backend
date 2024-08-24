package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="time_partida")
@NoArgsConstructor
@AllArgsConstructor
public class TimePartida implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_time_partida")
    @Setter @Getter
    private Long idTimePartida;

    @Column(name="pontuacao", insertable = false)
    @Setter @Getter
    private int pontuacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time", updatable = false, nullable = false)
    @Setter @Getter
    private Time time;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_partida", updatable = false, nullable = false)
    @Setter @Getter
    private Partida partida;

    @Column(name="id_resultado_time_partida", nullable = false)
    @Setter @Getter
    private ResultadoTimePartida resultadoTimePartida;
}
