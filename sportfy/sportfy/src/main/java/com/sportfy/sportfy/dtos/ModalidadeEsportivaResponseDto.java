package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.ModalidadeEsportiva;
import java.time.OffsetDateTime;

public record ModalidadeEsportivaResponseDto(
    Long idModalidadeEsportiva,
    String nome,
    String descricao,
    String foto,
    OffsetDateTime dataCriacao
) {
    public static ModalidadeEsportivaResponseDto toDTO(ModalidadeEsportiva modalidadeEsportiva) {
        return new ModalidadeEsportivaResponseDto(
            modalidadeEsportiva.getIdModalidadeEsportiva(),
            modalidadeEsportiva.getNome(),
            modalidadeEsportiva.getDescricao(),
            modalidadeEsportiva.getFoto(),
            modalidadeEsportiva.getDataCriacao()
        );
    }

}
