package com.sportfy.sportfy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="campeonato_partida")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampeonatoPartida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campeonato_partida")
    private Long id;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato")
    private Campeonato idCampeonato;

    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_partida")
    private Partida idPartida;
}
