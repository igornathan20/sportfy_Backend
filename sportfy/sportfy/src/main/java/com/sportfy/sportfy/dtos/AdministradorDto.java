package com.sportfy.sportfy.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.sportfy.sportfy.enums.TipoPermissao;

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
    
}
