package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Regra;
import jakarta.validation.constraints.Size;

public record RegraDto(
    Long idRegra,

    @Size(min = 1, max = 50, message = "Título deve ter entre 1 e 50 caracteres")
    String titulo,

    @Size(min = 1, max = 255, message = "Descrição deve ter entre 1 e 255 caracteres")
    String descricao,

    Long idModalidadeEsportiva
) {
    public static RegraDto fromRegraBD(Regra regra) {
        return new RegraDto(
            regra.getIdRegra(),
            regra.getTitulo(),
            regra.getDescricao(),
            regra.getModalidadeEsportiva().getIdModalidadeEsportiva()
        );
    }
}
