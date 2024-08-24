package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoFasePartida;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="fase_partida")
@NoArgsConstructor
@AllArgsConstructor
public class FasePartida implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_fase_partida")
    @Setter @Getter
    private Long idFasePartida;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_fase_partida", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoFasePartida tipoFasePartida;
}
