package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Notificacao;

public record NotificacaoDto(
     Long idAcademico,
     boolean modalidadeEsportivas,
     boolean campeonatos
) {
    public static NotificacaoDto fromNotificacao(Notificacao notificacao){
        return new NotificacaoDto(
                notificacao.getIdAcademico(),
                notificacao.isModalidadeEsportivas(),
                notificacao.isCampeonatos()
        );
    }
}
