package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record EstatisticasPessoaisDto(
        OffsetDateTime dataInscricaoSistema,
        int participacoesCampeonatos,
        int totalModalidadesInscritas,
        int totalMetasDiariasRealizadas,
        int totalMetasDiariasEmAberto,
       // int totalMetasEsportivasRealizadas,
        int postsRealizados
) {
}
