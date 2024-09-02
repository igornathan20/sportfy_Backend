package com.sportfy.sportfy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="notificacao")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_notificacao")
    private Long idNotificacao;
    @Column(name="idAcademico", nullable = false, unique = true)
    private Long idAcademico;
    //@Enumerated(EnumType.STRING)
    //@Column(name="tipo_notificacao", insertable = false, updatable = false, nullable = false, unique = true)
    //private TipoNotificacao tipoNotificacao;
    @Column(name="modalidadeEsportivas")
    private boolean modalidadeEsportivas = true;
    @Column(name="campeonatos")
    private boolean campeonatos = true;
}
