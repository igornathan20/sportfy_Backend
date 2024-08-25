package com.sportfy.sportfy.dtos;

public record EnderecoDto (
    String cep,
    String uf,
    String cidade,
    String bairro,
    String rua
) {
    public static EnderecoDto fromApiDto(EnderecoApiDto enderecoApiDto) {
        return new EnderecoDto(
            enderecoApiDto.cep(),
            enderecoApiDto.uf(),
            enderecoApiDto.cidade(),
            enderecoApiDto.bairro(),
            enderecoApiDto.rua()
        );
    }
}
