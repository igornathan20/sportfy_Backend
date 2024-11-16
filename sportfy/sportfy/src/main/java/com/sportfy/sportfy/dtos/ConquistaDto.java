package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;
import com.sportfy.sportfy.models.Conquista;

public record ConquistaDto (
    Long idConquista,
    Integer progressoAtual,
    OffsetDateTime dataConquista,
    Boolean conquistado,
    Long idAcademico,
    MetaEsportivaDto metaEsportiva
) {
    public static ConquistaDto fromConquistaBD(Conquista conquista) {
        if (conquista.getDataConquista() == null) {
            return new ConquistaDto(
                conquista.getIdConquista(),
                conquista.getProgressoAtual(),
                null,
                conquista.isConquistado(),
                conquista.getAcademico().getIdAcademico(),
                MetaEsportivaDto.toDto(conquista.getMetaEsportiva())
            );
        }
        return new ConquistaDto(
            conquista.getIdConquista(),
            conquista.getProgressoAtual(),
            conquista.getDataConquista(),
            conquista.isConquistado(),
            conquista.getAcademico().getIdAcademico(),
            MetaEsportivaDto.toDto(conquista.getMetaEsportiva())
        );
    }
}
