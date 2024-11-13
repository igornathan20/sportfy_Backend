package com.sportfy.sportfy.dtos;

public record EstatisticasPessoaisModalidadeDto(
        String modalidade,
        int vitorias,
        int derrotas,
        int jogos,
        AvaliacaoResponseDto avaliacao
) {
}
