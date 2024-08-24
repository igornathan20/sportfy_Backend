package com.sportfy.sportfy.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="academico")
@NoArgsConstructor
@AllArgsConstructor
public class Academico extends Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico")
    @Setter @Getter
    private Long idAcademico;

    @Column(name="curso")
    @Setter @Getter
    private String curso;
}
