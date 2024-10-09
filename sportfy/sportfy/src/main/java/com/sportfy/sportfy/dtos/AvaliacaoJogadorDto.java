package com.sportfy.sportfy.dtos;

public record AvaliacaoJogadorDto(
        Long idAvaliacao,
        Long idJogador,
        int nota,
        Long idAvaliador
) {}
