package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.ModalidadeEsportiva;

import java.time.OffsetDateTime;

import java.util.*;

import java.util.stream.Collectors;

public record ModalidadeEsportivaDto(
    Long idModalidadeEsportiva,
    String nome,
    String descricao,
    String foto,
    OffsetDateTime dataCriacao,
    List<RegraDto> listaRegra,
    List<MetaEsportivaDto> listaMetaEsportiva
) {
    public static ModalidadeEsportivaDto toDTO(ModalidadeEsportiva modalidadeEsportiva) {
        List<RegraDto> listaRegra = new ArrayList<RegraDto>();
        List<MetaEsportivaDto> listaMetaEsportiva = new ArrayList<MetaEsportivaDto>();
        if (modalidadeEsportiva.getListaRegra() != null && !modalidadeEsportiva.getListaRegra().isEmpty()) {
            listaRegra = modalidadeEsportiva.getListaRegra().stream().map(regra -> RegraDto.fromRegraBD(regra)).collect(Collectors.toList());
        }
        if (modalidadeEsportiva.getListaMetaEsportiva() != null && !modalidadeEsportiva.getListaMetaEsportiva().isEmpty()) {
            listaMetaEsportiva = modalidadeEsportiva.getListaMetaEsportiva().stream().map(metaEsportiva -> MetaEsportivaDto.fromMetaEsportivaBD(metaEsportiva)).collect(Collectors.toList());
        }
        return new ModalidadeEsportivaDto(
            modalidadeEsportiva.getIdModalidadeEsportiva(),
            modalidadeEsportiva.getNome(),
            modalidadeEsportiva.getDescricao(),
            modalidadeEsportiva.getFoto(),
            modalidadeEsportiva.getDataCriacao(),
            listaRegra,
            listaMetaEsportiva
        );
    }

}
