package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record EstatisticasPessoaisSistemaDto(
        OffsetDateTime dataInscricaoSistema,
        int inscricoesEmCampeonatos,
        int postsRealizados,
        int jogos
) {
}
