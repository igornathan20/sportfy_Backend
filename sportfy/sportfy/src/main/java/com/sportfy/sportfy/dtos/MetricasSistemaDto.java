package com.sportfy.sportfy.dtos;

public record MetricasSistemaDto (
    Integer quantidadeModalidadesCadastradas,
    Integer quantidadeAcademicosCadastrados,
    Integer quantidadePublicacoes,
    Integer totalCampeonatosAbertos,
    Integer totalCampeonatosFinalizados
) {
}
