package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Notificacao;

public record NotificacaoDto(
     Long idAcademico,
     boolean campeonatos,
     boolean publicacoes,
     boolean comentarios,
     boolean curtidas
) {
    public static NotificacaoDto toEntity(Notificacao notificacao){
        return new NotificacaoDto(
                notificacao.getIdAcademico(),
                notificacao.isNotificarCampeonatos(),
                notificacao.isNotificarPublicacoes(),
                notificacao.isNotificarComentarios(),
                notificacao.isNotificarCurtidas()
        );
    }
}
