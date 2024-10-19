package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoSituacaoJogador;

import java.util.List;

public record JogadorDto(
        Long idJogador,
        Long idModalidadeEsportiva,
        TipoSituacaoJogador situacaoJogador,
        int pontuacao,
        Long idTime,
        Long idAcademico
) {}
