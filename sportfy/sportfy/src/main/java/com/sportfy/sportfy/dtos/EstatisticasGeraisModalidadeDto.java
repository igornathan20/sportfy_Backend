package com.sportfy.sportfy.dtos;

public record EstatisticasGeraisModalidadeDto(
        String modalidadeEsportiva,
        int numeroCampeonatos,
        int numeroPartidas,
        int inscritosModalidade
) {
}
