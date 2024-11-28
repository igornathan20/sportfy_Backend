package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Academico;
import java.time.OffsetDateTime;
import java.util.List;

public record AcademicoResponseDto(
        Long idAcademico,
        String curso,
        String username,
        String email,
        String nome,
        String genero,
        String telefone,
        String foto,
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
                academico.getUsuario().getFoto(),
                academico.getUsuario().getDataNascimento(),
                academico.getUsuario().getDataCriacao(),
                academico.getUsuario().isAtivo(),
                academico.getUsuario().getPermissao(),
                academico.getModalidadeEsportivas() == null || academico.getModalidadeEsportivas().isEmpty()
                        ? List.of()
                        : academico.getModalidadeEsportivas().stream()
                        .map(m -> new ModalidadeAcademicoDto(m.getIdModalidadeEsportiva(), m.getNome()))
                        .toList());
        }

    }


