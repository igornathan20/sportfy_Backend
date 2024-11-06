package com.sportfy.sportfy.dtos;

public record EstatisticaPorModalidadeEsportivaDto (
    String nomeModalidadeEsportiva,
    String fotoModalidadeEsportiva,
    Integer totalMetasEsportivasInscritas,
    Integer totalConquistasAlcancadas,
    Integer totalCampeonatosCriados,
    Integer totalCampeonatosParticipados
) {

}
