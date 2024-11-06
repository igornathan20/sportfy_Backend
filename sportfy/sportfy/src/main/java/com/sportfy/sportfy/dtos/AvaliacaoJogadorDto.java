package com.sportfy.sportfy.dtos;

public record AvaliacaoJogadorDto(
        Long idAvaliacao,
        Long idAcademico,
        Long idModalidade,
        int nota,
        Long idAvaliador
) {}
