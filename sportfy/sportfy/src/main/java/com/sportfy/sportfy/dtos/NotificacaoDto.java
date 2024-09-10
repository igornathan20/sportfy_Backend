package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Notificacao;

public record NotificacaoDto(
     Long idAcademico,
     boolean campeonatos,
     boolean posts,
     boolean comentarios,
     boolean likes

) {
    public static NotificacaoDto fromNotificacao(Notificacao notificacao){
        return new NotificacaoDto(
                notificacao.getIdAcademico(),
                notificacao.isNotificarCampeonatos(),
                notificacao.isNotificarPosts(),
                notificacao.isNotificarComentarios(),
                notificacao.isNotificarLikes()
        );
    }
}
