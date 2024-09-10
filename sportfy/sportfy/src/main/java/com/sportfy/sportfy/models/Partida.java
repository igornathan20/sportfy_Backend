package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoFasePartida;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="partida")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Partida implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_partida")
    private Long idPartida;

    @Column(name="data_partida", nullable = false)
    private OffsetDateTime dataPartida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato", nullable = false)
    private Campeonato campeonato;

    @Enumerated(EnumType.STRING)
    @Column(name="fase_partida")
    private TipoFasePartida fasePartida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time1")
    private Time time1;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time2")
    private Time time2;

//    @Column(name="pontuacao_time1")
//    private int pontuacaoTime1;
//
//    @Column(name="pontuacao_time2")
//    private int pontuacaoTime2;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_resultado")
    private Resultado resultado;

}
