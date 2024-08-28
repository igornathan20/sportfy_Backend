package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Academico;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.hibernate.validator.constraints.br.*;

public record AcademicoDto(
    Long idAcademico,

    String curso,

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 4, max = 30, message = "Username deve ter entre 4 e 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username deve conter apenas letras, números, sublinhados (_), hífens (-), e pontos (.)")
    String username,

    @Email(message = "Email inválido")
    @Size(min = 9, max = 100, message = "Email deve ter entre 9 e 100 caracteres")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Email deve conter apenas letras minúsculas, números, sublinhados (_), hífens (-), e pontos (.)")
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 4, max = 100, message = "Senha deve ter entre 4 e 100 caracteres")
    String password,

    @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u00FF\\s]+$", message = "Nome inválido")
    @Size(min = 4, max = 100, message = "Nome deve ter entre 4 e 100 caracteres")
    String nome,

    @CPF(message = "CPF inválido")
    String cpf,

    @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres: 'XX XXXXX-XXXX'")
    String telefone,

    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate dataNascimento,

    @Size(min = 0, max = 255, message = "Caminho da foto deve ter no máximo 255 caracteres")
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
