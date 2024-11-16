package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoCanal;
import com.sportfy.sportfy.models.Canal;
import java.util.*;

public record CanalDto(
    Long idCanal,
    TipoCanal tipoCanal,
    List<UsuarioDto> listaUsuario
) {
    public static CanalDto fromCanalBD(Canal canal) {
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        if (canal.getListaUsuarioCanal() != null && !canal.getListaUsuarioCanal().isEmpty()) {
            listaUsuario = canal.getListaUsuarioCanal().stream().map(usuarioCanal -> UsuarioDto.fromUsuarioBD(usuarioCanal.getUsuario())).toList();
        }
        return new CanalDto(
            canal.getIdCanal(),
            canal.getTipoCanal(),
            listaUsuario
        );
    }
}
