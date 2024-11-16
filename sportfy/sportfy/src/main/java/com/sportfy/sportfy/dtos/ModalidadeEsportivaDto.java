package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.ModalidadeEsportiva;
import java.time.OffsetDateTime;

public record ModalidadeEsportivaDto(
    Long idModalidadeEsportiva,
    String nome,
    String descricao,
    String foto,
    OffsetDateTime dataCriacao
) {
    public static ModalidadeEsportivaDto toDTO(ModalidadeEsportiva modalidadeEsportiva) {

        return new ModalidadeEsportivaDto(
            modalidadeEsportiva.getIdModalidadeEsportiva(),
            modalidadeEsportiva.getNome(),
            modalidadeEsportiva.getDescricao(),
            modalidadeEsportiva.getFoto(),
            modalidadeEsportiva.getDataCriacao()
        );
    }

}
