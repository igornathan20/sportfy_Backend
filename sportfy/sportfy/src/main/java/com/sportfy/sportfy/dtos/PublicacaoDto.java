package com.sportfy.sportfy.dtos;

import java.util.*;

import com.sportfy.sportfy.models.Publicacao;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

public record PublicacaoDto(
    Long idPublicacao,

    @NotBlank(message = "Descrição é obrigatório")
    @Size(min = 1, max = 500, message = "Descrição deve ter entre 1 e 500 caracteres")
    String descricao,
    
    OffsetDateTime dataPublicacao,

    @NotNull(message = "O campo idCanal é obrigatório")
    @Min(value = 1, message = "O idCanal deve ser maior que 0")
    Long idCanal,

    @NotNull(message = "O campo Usuario é obrigatório")
    @Valid
    UsuarioDto Usuario,

    List<UsuarioDto> listaUsuarioCurtida,

    List<ComentarioDto> listaComentario
) {
    public static PublicacaoDto fromPublicacaoBD(Publicacao publicacao) {
        List<UsuarioDto> listaUsuarioCurtida = new ArrayList<UsuarioDto>();
        List<ComentarioDto> listaComentario = new ArrayList<ComentarioDto>();
        if (publicacao.getListaCurtidaPublicacao() != null && !publicacao.getListaCurtidaPublicacao().isEmpty()) {
            listaUsuarioCurtida = publicacao.getListaCurtidaPublicacao().stream().map(curtidaPublicacao -> UsuarioDto.fromUsuarioBD(curtidaPublicacao.getUsuario())).collect(Collectors.toList());
        }
        if (publicacao.getListaComentario() != null && !publicacao.getListaComentario().isEmpty()) {
            listaComentario = publicacao.getListaComentario().stream().map(comentario -> ComentarioDto.fromComentarioBD(comentario)).collect(Collectors.toList());
        }
        return new PublicacaoDto(
            publicacao.getIdPublicacao(),
            publicacao.getDescricao(),
            publicacao.getDataPublicacao(),
            publicacao.getCanal().getIdCanal(),
            UsuarioDto.fromUsuarioBD(publicacao.getUsuario()),
            listaUsuarioCurtida,
            listaComentario
        );
    }
}
