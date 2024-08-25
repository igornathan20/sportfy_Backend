package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Academico;

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
    public static AcademicoDto fromAcademicoBD(Academico academico) {
        return new AcademicoDto(
            academico.getIdAcademico(),
            academico.getCurso(),
            academico.getUsuario().getUsername(),
            academico.getUsuario().getEmail(),
            null,
            academico.getUsuario().getNome(),
            academico.getUsuario().getCpf(),
            academico.getUsuario().getTelefone(),
            academico.getUsuario().getDataNascimento(),
            academico.getUsuario().getFoto(),
            academico.getUsuario().getDataCriacao(),
            academico.getUsuario().isAtivo(),
            academico.getUsuario().getPermissao().getTipoPermissao()
        );
    }
}
