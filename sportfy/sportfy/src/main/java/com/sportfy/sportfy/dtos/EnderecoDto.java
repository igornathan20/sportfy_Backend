package com.sportfy.sportfy.dtos;

public record EnderecoDto (
    String cep,
    String uf,
    String cidade,
    String bairro,
    String rua,
    int numero,
    String complemento

) {
    public static EnderecoDto fromApiViaCepDto(EnderecoApiViaCepDto enderecoApiViaCepDto) {
        return new EnderecoDto(
            enderecoApiViaCepDto.cep(),
            enderecoApiViaCepDto.uf(),
            enderecoApiViaCepDto.cidade(),
            enderecoApiViaCepDto.bairro(),
            enderecoApiViaCepDto.rua(),
            0,
            null
        );
    }
}
