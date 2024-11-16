package com.sportfy.sportfy.dtos;

import java.util.*;
import com.sportfy.sportfy.models.Publicacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;

public record PublicacaoDto(
    Long idPublicacao,

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 1, max = 50, message = "Título deve ter entre 1 e 50 caracteres")
    String titulo,

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

    Long idModalidadeEsportiva,

    List<UsuarioDto> listaUsuarioCurtida
) {
    public static PublicacaoDto fromPublicacaoBD(Publicacao publicacao) {
        List<UsuarioDto> listaUsuarioCurtida = new ArrayList<>();
        if (publicacao.getListaCurtidaPublicacao() != null && !publicacao.getListaCurtidaPublicacao().isEmpty()) {
            listaUsuarioCurtida = publicacao.getListaCurtidaPublicacao().stream().map(curtidaPublicacao -> UsuarioDto.fromUsuarioBD(curtidaPublicacao.getUsuario())).toList();
        }
        return new PublicacaoDto(
            publicacao.getIdPublicacao(),
            publicacao.getTitulo(),
            publicacao.getDescricao(),
            publicacao.getDataPublicacao(),
            publicacao.getCanal().getIdCanal(),
            UsuarioDto.fromUsuarioBD(publicacao.getUsuario()),
            publicacao.getIdModalidadeEsportiva(),
            listaUsuarioCurtida
        );
    }
}
