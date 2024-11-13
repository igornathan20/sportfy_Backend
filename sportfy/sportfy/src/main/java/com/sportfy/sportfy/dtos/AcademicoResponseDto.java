package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Academico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        TipoPermissao permissao,
        List<ModalidadeAcademicoDto> modalidades
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
                academico.getUsuario().getPermissao(),
                academico.getModalidadeEsportivas() == null || academico.getModalidadeEsportivas().isEmpty()
                        ? List.of()
                        : academico.getModalidadeEsportivas().stream()
                        .map(m -> new ModalidadeAcademicoDto(m.getIdModalidadeEsportiva(), m.getNome()))
                        .collect(Collectors.toList()));
        }

    }


