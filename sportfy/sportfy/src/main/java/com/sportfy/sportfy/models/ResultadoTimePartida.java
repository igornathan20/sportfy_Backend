package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoResultadoTimePartida;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="resultado_time_partida")
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoTimePartida implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_resultado_time_partida")
    @Setter @Getter
    private Long idResultadoTimePartida;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_resultado_time_partida", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoResultadoTimePartida tipoResultadoTimePartida;
}
