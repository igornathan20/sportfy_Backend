package com.sportfy.sportfy.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
public record EnderecoApiViaCepDto (
    @JsonProperty("cep") String cep,
    @JsonProperty("uf") String uf,
    @JsonProperty("localidade") String cidade,
    @JsonProperty("bairro") String bairro,
    @JsonProperty("logradouro") String rua
) {

}
