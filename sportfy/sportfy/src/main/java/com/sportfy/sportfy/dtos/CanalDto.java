package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoCanal;
import com.sportfy.sportfy.models.Canal;

import java.util.*;

import java.util.stream.Collectors;

public record CanalDto(
    Long idCanal,
    TipoCanal tipoCanal,
    List<UsuarioDto> listaUsuario
) {
    public static CanalDto fromCanalBD(Canal canal) {
        List<UsuarioDto> listaUsuario = new ArrayList<UsuarioDto>();
        if (canal.getListaUsuarioCanal() != null && !canal.getListaUsuarioCanal().isEmpty()) {
            listaUsuario = canal.getListaUsuarioCanal().stream().map(usuarioCanal -> UsuarioDto.fromUsuarioBD(usuarioCanal.getUsuario())).collect(Collectors.toList());
        }
        return new CanalDto(
            canal.getIdCanal(),
            canal.getTipoCanal(),
            listaUsuario
        );
    }
}
