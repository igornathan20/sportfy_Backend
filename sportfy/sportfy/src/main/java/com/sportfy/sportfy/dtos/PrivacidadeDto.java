package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Privacidade;

public record PrivacidadeDto(
        Long idAcademico,
        boolean mostrarModalidadesEsportivas,
        boolean mostrarHistoricoCampeonatos,
        boolean mostrarEstatisticasModalidadesEsportivas,
        boolean mostrarConquistas
) {
    public static PrivacidadeDto fromPrivacidade(Privacidade privacidade){
        return new PrivacidadeDto(
                privacidade.getIdAcademico(),
                privacidade.isMostrarModalidadesEsportivas(),
                privacidade.isMostrarHistoricoCampeonatos(),
                privacidade.isMostrarEstatisticasModalidadesEsportivas(),
                privacidade.isMostrarConquistas()
        );
    }

}