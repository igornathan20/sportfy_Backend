package com.sportfy.sportfy.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Administrador;

public record AdministradorDto(
    Long idAdministrador,
    String username,
    String email,
    String password,
    String nome,
    String cpf,
    String telefone,
    LocalDate dataNascimento,
    String foto,
    OffsetDateTime dataCriacao,
    boolean ativo,
    TipoPermissao permissao
) {
    public static AdministradorDto fromAdministradorBD(Administrador administrador) {
        return new AdministradorDto(
            administrador.getIdAdministrador(),
            administrador.getUsuario().getUsername(),
            administrador.getUsuario().getEmail(),
            null,
            administrador.getUsuario().getNome(),
            administrador.getUsuario().getCpf(),
            administrador.getUsuario().getTelefone(),
            administrador.getUsuario().getDataNascimento(),
            administrador.getUsuario().getFoto(),
            administrador.getUsuario().getDataCriacao(),
            administrador.getUsuario().isAtivo(),
            administrador.getUsuario().getPermissao().getTipoPermissao()
        );
    }
}
