package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record CampeonatoDto(
        Long idCampeonato,
        String codigo,
        String senha,
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
        Long idModalidadeEsportiva,
        String situacaoCampeonato
) {
}
