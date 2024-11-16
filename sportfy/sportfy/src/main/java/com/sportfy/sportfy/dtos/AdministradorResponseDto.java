package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Administrador;
import java.time.OffsetDateTime;

public record AdministradorResponseDto (
        Long idAdministrador,
        String username,
        String nome,
        String telefone,
        OffsetDateTime dataNascimento,
        String foto,
        OffsetDateTime dataCriacao,
        boolean ativo,
        TipoPermissao permissao
) {
public static AdministradorResponseDto fromEntity(Administrador administrador) {
    return new AdministradorResponseDto(
            administrador.getIdAdministrador(),
            administrador.getUsuario().getUsername(),
            administrador.getUsuario().getNome(),
            administrador.getUsuario().getTelefone(),
            administrador.getUsuario().getDataNascimento(),
            administrador.getUsuario().getFoto(),
            administrador.getUsuario().getDataCriacao(),
            administrador.getUsuario().isAtivo(),
            administrador.getUsuario().getPermissao()
    );
}
}