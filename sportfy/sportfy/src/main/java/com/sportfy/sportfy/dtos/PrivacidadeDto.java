package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Privacidade;

public record PrivacidadeDto(
        Long idAcademico,
        boolean mostrarHistoricoCampeonatos,
        boolean mostrarEstatisticasModalidadesEsportivas,
        boolean mostrarConquistas
) {
    public static PrivacidadeDto fromPrivacidade(Privacidade privacidade){
        return new PrivacidadeDto(
                privacidade.getIdAcademico(),
                privacidade.isMostrarHistoricoCampeonatos(),
                privacidade.isMostrarEstatisticasModalidadesEsportivas(),
                privacidade.isMostrarConquistas()
        );
    }

}