package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record AcademicoDto(
    Long idAcademico,
    String curso,
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
