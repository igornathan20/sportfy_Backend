package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record CampeonatoDto(
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
        int privacidadeCampeonato,
        Long idAcademico,
        Long idModalidadeEsportiva,
        int situacaoCampeonato
) {
}
