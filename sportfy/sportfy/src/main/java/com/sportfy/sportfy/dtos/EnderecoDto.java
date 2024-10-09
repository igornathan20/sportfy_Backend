package com.sportfy.sportfy.dtos;

import com.sportfy.sportfy.models.Endereco;

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
    public static EnderecoDto fromEntity(Endereco endereco) {
        return new EnderecoDto(
                endereco.getCep(),
                endereco.getUf(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento()
        );
    }

}
