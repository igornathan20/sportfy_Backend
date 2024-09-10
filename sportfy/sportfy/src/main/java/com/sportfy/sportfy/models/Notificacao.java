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

    @Column(name="campeonatos")
    private boolean notificarCampeonatos = true;
    @Column(name="posts")
    private boolean notificarPosts = true;
    @Column(name="comentarios")
    private boolean notificarComentarios = true;
    @Column(name="likes")
    private boolean notificarLikes = true;

}
