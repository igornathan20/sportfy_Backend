package com.sportfy.sportfy.models;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "academico")
@NoArgsConstructor
@AllArgsConstructor
public class Academico extends Usuario {
    // @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    // @Column(name="id_academico")
    // @Setter @Getter
    // private Long idAcademico;

    @Column(name="curso")
    @Setter @Getter
    private String curso;
}
