package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Roles;
import java.time.LocalDate;

public record AcademicoDto(
    Long id,
    String username,
    String email,
    String password,
    String nome,
    String cpf,
    String telefone,
    LocalDate dataCriacao,
    Roles userRole
) {
}
