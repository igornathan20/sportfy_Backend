package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetaEsportivaConquistaDto {
    private Boolean conquistado;
    private OffsetDateTime dataConquista;
    private MetaEsportivaDto metaEsportiva;
    private ModalidadeEsportivaDto modalidadeEsportiva;
}
