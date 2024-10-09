package com.sportfy.sportfy.dtos;

import java.util.List;

public record JogadorDto(
        Long idJogador,
        Long idModalidadeEsportiva,
        int pontuacao,
        Long idTime,
        Long idAcademico
) {}
