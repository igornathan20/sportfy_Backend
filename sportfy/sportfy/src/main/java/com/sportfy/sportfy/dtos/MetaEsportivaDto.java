package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.MetaEsportiva;
import jakarta.validation.constraints.Size;

public record MetaEsportivaDto(
    Long idMetaEsportiva,

    @Size(min = 1, max = 50, message = "Título deve ter entre 1 e 50 caracteres")
    String titulo,

    @Size(min = 1, max = 255, message = "Descrição deve ter entre 1 e 255 caracteres")
    String descricao,

    String foto,

    Long idModalidadeEsportiva
) {
    public static MetaEsportivaDto toDto(MetaEsportiva metaEsportiva) {
        return new MetaEsportivaDto(
            metaEsportiva.getIdMetaEsportiva(),
            metaEsportiva.getTitulo(),
            metaEsportiva.getDescricao(),
            metaEsportiva.getFoto(),
            metaEsportiva.getModalidadeEsportiva().getIdModalidadeEsportiva()
        );
    }
}
