package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="academico_campeonato")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoCampeonato implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_campeonato")
    @Setter @Getter
    private Long idAcademicoCampeonato;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato", updatable = false, nullable = false)
    @Setter @Getter
    private Campeonato campeonato;
}
