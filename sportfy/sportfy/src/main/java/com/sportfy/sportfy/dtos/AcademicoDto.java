package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
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
    TipoPermissao permissao
) {
}
