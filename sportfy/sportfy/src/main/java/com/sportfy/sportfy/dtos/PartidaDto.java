package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record PartidaDto(
        Long idPartida,
        OffsetDateTime dataPartida,
        Long idCampeonato,
        String fasePartida,
        Long idTime1,
        Long idTime2,
        String situacaoPartida,
        ResultadoDto resultado
) {}
