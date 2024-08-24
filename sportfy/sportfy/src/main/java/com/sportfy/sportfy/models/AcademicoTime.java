package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="academico_time")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoTime implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_time")
    @Setter @Getter
    private Long idAcademicoTime;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time", updatable = false, nullable = false)
    @Setter @Getter
    private Time time;
}
