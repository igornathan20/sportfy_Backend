package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record CampeonatoResponseDto(
        Long idCampeonato,
        String codigo,
        String titulo,
        String descricao,
        String aposta,
        OffsetDateTime dataCriacao,
        OffsetDateTime dataInicio,
        OffsetDateTime dataFim,
        int limiteTimes,
        int limiteParticipantes,
        boolean ativo,
        EnderecoDto endereco,
        String privacidadeCampeonato,
        Long idAcademico,
        String usernameCriador,
        Long idModalidadeEsportiva,
        String situacaoCampeonato
) {
}
