package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoCanal;
import com.sportfy.sportfy.models.Canal;

import java.util.*;

import java.util.stream.Collectors;

public record CanalDto(
    Long idCanal,
    TipoCanal tipoCanal,
    List<UsuarioDto> listaUsuario,
    List<PublicacaoDto> listaPublicacao
) {
    public static CanalDto fromCanalBD(Canal canal) {
        List<UsuarioDto> listaUsuario = new ArrayList<UsuarioDto>();
        List<PublicacaoDto> listaPublicacao = new ArrayList<PublicacaoDto>();
        if (canal.getListaUsuarioCanal() != null && !canal.getListaUsuarioCanal().isEmpty()) {
            listaUsuario = canal.getListaUsuarioCanal().stream().map(usuarioCanal -> UsuarioDto.fromUsuarioBD(usuarioCanal.getUsuario())).collect(Collectors.toList());
        }
        if (canal.getListaPublicacao() != null && !canal.getListaPublicacao().isEmpty()) {
            listaPublicacao = canal.getListaPublicacao().stream().map(publicacao -> PublicacaoDto.fromPublicacaoBD(publicacao)).collect(Collectors.toList());
        }
        return new CanalDto(
            canal.getIdCanal(),
            canal.getTipoCanal(),
            listaUsuario,
            listaPublicacao
        );
    }
}
