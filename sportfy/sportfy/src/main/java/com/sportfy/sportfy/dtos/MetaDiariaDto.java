package com.sportfy.sportfy.dtos;

public record MetaDiariaDto(
         Long idMetaDiaria,
         String titulo,
         String objetivo,
         int quantidadeConcluido,
         int progressoAtual,
         int progressoMaximo,
         String progressoItem,
         Long idAcademico,
         int situacaoMetaDiaria
) {

}
