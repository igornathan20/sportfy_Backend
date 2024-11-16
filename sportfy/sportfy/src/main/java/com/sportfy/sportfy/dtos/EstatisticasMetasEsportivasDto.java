package com.sportfy.sportfy.dtos;

import java.util.List;

public record EstatisticasMetasEsportivasDto (
    Integer totalModalidadesEsportivasInscritas,
    Integer totalMetasEsportivasInscritas,
    Integer totalConquistasAlcancadas,
    Integer totalCampeonatosCriados,
    Integer totalCampeonatosParticipados,
    List<EstatisticaPorModalidadeEsportivaDto> listaEstatisticaPorModalidadeEsportivaDto
) {
}
