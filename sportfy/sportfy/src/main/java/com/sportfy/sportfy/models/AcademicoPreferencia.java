package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="academico_preferencia")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoPreferencia implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_preferencia")
    @Setter @Getter
    private Long idAcademicoPreferencia;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_preferencia", updatable = false, nullable = false)
    @Setter @Getter
    private Preferencia preferencia;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;
}