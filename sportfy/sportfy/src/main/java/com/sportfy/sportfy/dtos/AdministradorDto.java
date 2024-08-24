package com.sportfy.sportfy.dtos;

import java.time.LocalDate;

import com.sportfy.sportfy.enums.TipoPermissao;

public record AdministradorDto(
    Long id,
    String username,
    String email,
    String password,
    String nome,
    String cpf,
    String telefone,
    LocalDate dataNascimento,
    String foto,
    TipoPermissao permissao
){}