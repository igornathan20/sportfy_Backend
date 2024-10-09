package com.sportfy.sportfy.dtos;

public record ResultadoDto(
        Long id,
        Long idVencedor,
        int pontuacaoTime1,
        int pontuacaoTime2,
        String descricao
) {}

