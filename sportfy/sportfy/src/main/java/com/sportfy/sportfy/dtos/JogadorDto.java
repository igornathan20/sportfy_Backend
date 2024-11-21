package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoSituacaoJogador;

public record JogadorDto(
        Long idJogador,
        String username,
        Long idModalidadeEsportiva,
        TipoSituacaoJogador situacaoJogador,
        int pontuacao,
        Long idTime,
        Long idAcademico
) {}
