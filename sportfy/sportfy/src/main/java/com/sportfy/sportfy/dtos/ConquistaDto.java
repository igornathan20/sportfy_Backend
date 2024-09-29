package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

import com.sportfy.sportfy.models.Conquista;

public record ConquistaDto (
    Long idConquista,
    OffsetDateTime dataConquista,
    Long idAcademico,
    MetaEsportivaDto metaEsportiva
) {
    public static ConquistaDto fromConquistaBD(Conquista conquista) {
        return new ConquistaDto(
            conquista.getIdConquista(),
            conquista.getDataConquista(),
            conquista.getAcademico().getIdAcademico(),
            MetaEsportivaDto.fromMetaEsportivaBD(conquista.getMetaEsportiva())
        );
    }
}
