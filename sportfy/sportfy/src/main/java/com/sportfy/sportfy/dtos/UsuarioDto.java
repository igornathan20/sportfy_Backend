package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Usuario;
import jakarta.validation.constraints.*;

public record UsuarioDto(
        @NotNull(message = "O campo idUsuario é obrigatório")
    @Min(value = 1, message = "O idUsuario deve ser maior que 0")
    Long idUsuario,

        String username,

        String nome,

        String foto,

        TipoPermissao permissao
) {
    public static UsuarioDto fromUsuarioBD(Usuario usuario) {
        return new UsuarioDto(
            usuario.getIdUsuario(),
            usuario.getUsername(),
            usuario.getNome(),
            usuario.getFoto(),
            usuario.getPermissao()
        );
    }
}
