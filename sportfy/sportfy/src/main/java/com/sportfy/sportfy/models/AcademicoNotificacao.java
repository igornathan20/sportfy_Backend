package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="academico_notificacao")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoNotificacao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_notificacao")
    @Setter @Getter
    private Long idAcademicoNotificacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_notificacao", updatable = false, nullable = false)
    @Setter @Getter
    private Notificacao notificacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    @Setter @Getter
    private Academico academico;
}
