package com.sportfy.sportfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sportfy.sportfy.dtos.EnderecoApiViaCepDto;
import com.sportfy.sportfy.dtos.EnderecoDto;
import com.sportfy.sportfy.exeptions.CepInvalidoException;
import com.sportfy.sportfy.exeptions.CepNaoExisteException;

@Service
public class EnderecoService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String CEP_PATTERN = "\\d{8}";
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    public EnderecoDto consultar(String cep) throws CepNaoExisteException, CepInvalidoException {
        if (!cep.matches(CEP_PATTERN)) {
            throw new CepInvalidoException("CEP inválido!");
        }
        String url = BASE_URL + cep + "/json/";
        EnderecoApiViaCepDto enderecoApiDto = null;

        try {
            enderecoApiDto = restTemplate.getForObject(url, EnderecoApiViaCepDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar o serviço de CEP: " + e.getMessage(), e);
        }

        if (enderecoApiDto == null || enderecoApiDto.cep() == null || enderecoApiDto.cep().isEmpty()) {
            throw new CepNaoExisteException("CEP não existe!");
        } 

        return EnderecoDto.fromApiViaCepDto(enderecoApiDto);
    }
}
