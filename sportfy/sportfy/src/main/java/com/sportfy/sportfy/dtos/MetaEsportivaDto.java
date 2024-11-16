package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.MetaEsportiva;
import jakarta.validation.constraints.Size;

public record MetaEsportivaDto(
    Long idMetaEsportiva,

    @Size(min = 1, max = 50, message = "Título deve ter entre 1 e 50 caracteres")
    String titulo,

    @Size(min = 1, max = 255, message = "Descrição deve ter entre 1 e 255 caracteres")
    String descricao,

    Integer progressoMaximo,

    String progressoItem,

    String foto,

    Boolean ativo,

    Long idModalidadeEsportiva
) {
    public static MetaEsportivaDto toDto(MetaEsportiva metaEsportiva) {
        return new MetaEsportivaDto(
            metaEsportiva.getIdMetaEsportiva(),
            metaEsportiva.getTitulo(),
            metaEsportiva.getDescricao(),
            metaEsportiva.getProgressoMaximo(),
            metaEsportiva.getProgressoItem(),
            metaEsportiva.getFoto(),
            metaEsportiva.isAtivo(),
            metaEsportiva.getModalidadeEsportiva().getIdModalidadeEsportiva()
        );
    }
}
