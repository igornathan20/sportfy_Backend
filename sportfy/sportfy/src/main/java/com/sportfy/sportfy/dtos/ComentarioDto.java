package com.sportfy.sportfy.dtos;

import java.util.*;
import com.sportfy.sportfy.models.Comentario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;

public record ComentarioDto(
    Long idComentario,

    @NotBlank(message = "Descrição é obrigatório")
    @Size(min = 1, max = 500, message = "Descrição deve ter entre 1 e 500 caracteres")
    String descricao,
    
    OffsetDateTime dataComentario,

    @NotNull(message = "O campo idPublicacao é obrigatório")
    @Min(value = 1, message = "O idPublicacao deve ser maior que 0")
    Long idPublicacao,

    @NotNull(message = "O campo Usuario é obrigatório")
    @Valid
    UsuarioDto Usuario,
    
    List<UsuarioDto> listaUsuarioCurtida
) {
    public static ComentarioDto fromComentarioBD(Comentario comentario) {
        List<UsuarioDto> listaUsuarioCurtida = new ArrayList<>();
        if (comentario.getListaCurtidaComentario() != null && !comentario.getListaCurtidaComentario().isEmpty()) {
            listaUsuarioCurtida = comentario.getListaCurtidaComentario().stream().map(curtidaComentario -> UsuarioDto.fromUsuarioBD(curtidaComentario.getUsuario())).toList();
        }
        return new ComentarioDto(
            comentario.getIdComentario(),
            comentario.getDescricao(),
            comentario.getDataComentario(),
            comentario.getPublicacao().getIdPublicacao(),
            UsuarioDto.fromUsuarioBD(comentario.getUsuario()),
            listaUsuarioCurtida
        );
    }
}
