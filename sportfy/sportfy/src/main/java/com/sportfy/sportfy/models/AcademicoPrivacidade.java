package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="academico_privacidade")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoPrivacidade implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_privacidade")
    @Setter @Getter
    private Long idAcademicoPrivacidade;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_privacidade", updatable = false, nullable = false)
    @Setter @Getter
    private Privacidade privacidade;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;
}