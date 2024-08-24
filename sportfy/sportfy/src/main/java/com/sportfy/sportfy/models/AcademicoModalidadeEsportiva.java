package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="academico_modalidade_esportiva")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoModalidadeEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_modalidade_esportiva")
    @Setter @Getter
    private Long idAcademicoModalidadeEsportiva;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    @Setter @Getter
    private ModalidadeEsportiva modalidadeEsportiva;
}
