package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Administrador;
import jakarta.validation.constraints.*;

public record AdministradorDto(
        Long idAdministrador,

        @NotBlank(message = "Username é obrigatório")
    @Size(min = 4, max = 30, message = "Username deve ter entre 4 e 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username deve conter apenas letras, números, sublinhados (_), hífens (-), e pontos (.)")
    String username,

        String password,

        @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u00FF\\s]+$", message = "Nome inválido")
    @Size(min = 4, max = 100, message = "Nome deve ter entre 4 e 100 caracteres")
    String nome,

        @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres: 'XX XXXXX-XXXX'")
    String telefone,

        @Past(message = "A data de nascimento deve estar no passado")
    OffsetDateTime dataNascimento,

        @Size(min = 0, max = 255, message = "Caminho da foto deve ter no máximo 255 caracteres")
    String foto,

        OffsetDateTime dataCriacao,

        boolean ativo,

        TipoPermissao permissao
) {
    public static AdministradorDto fromAdministradorBD(Administrador administrador) {
        return new AdministradorDto(
            administrador.getIdAdministrador(),
            administrador.getUsuario().getUsername(),
            null,
            administrador.getUsuario().getNome(),
            administrador.getUsuario().getTelefone(),
            administrador.getUsuario().getDataNascimento(),
            administrador.getUsuario().getFoto(),
            administrador.getUsuario().getDataCriacao(),
            administrador.getUsuario().isAtivo(),
            administrador.getUsuario().getPermissao()
        );
    }
}
