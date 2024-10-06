package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Academico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record AcademicoResponseDto(
    Long idAcademico,
    String curso,
    String username,
    String email,
    String nome,
    String genero,
    String telefone,
    OffsetDateTime dataNascimento,
    OffsetDateTime dataCriacao,
    boolean ativo,
    TipoPermissao permissao
) {
    public static AcademicoResponseDto fromAcademicoBD(Academico academico) {
        return new AcademicoResponseDto(
                academico.getIdAcademico(),
                academico.getCurso(),
                academico.getUsuario().getUsername(),
                academico.getEmail(),
                academico.getUsuario().getNome(),
                academico.getUsuario().getGenero(),
                academico.getUsuario().getTelefone(),
                academico.getUsuario().getDataNascimento(),
                academico.getUsuario().getDataCriacao(),
                academico.getUsuario().isAtivo(),
                academico.getUsuario().getPermissao().getTipoPermissao()
        );
    }
}
