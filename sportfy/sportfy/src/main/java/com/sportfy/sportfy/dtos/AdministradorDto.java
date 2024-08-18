package com.sportfy.sportfy.dtos;

public record AdministradorDto(
    Long id,
    String username,
    String email,
    String password,
    String nome,
    String cpf,
    String telefone,
    int userRole
){}