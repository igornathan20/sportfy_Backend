package com.sportfy.sportfy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.sportfy.sportfy.dtos.EnderecoApiDto;
import com.sportfy.sportfy.exeptions.CepInvalidoException;
import com.sportfy.sportfy.exeptions.CepNaoExisteException;

@Component
public class ConsultarEndereco {

    @Autowired
    private RestTemplate restTemplate;

    private static final String CEP_PATTERN = "\\d{8}";
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    public EnderecoApiDto consultarEndereco(String cep) throws CepNaoExisteException, CepInvalidoException {
        if (!cep.matches(CEP_PATTERN)) {
            throw new CepInvalidoException("CEP inválido!");
        }

        String url = BASE_URL + cep + "/json/";

        try {
            EnderecoApiDto enderecoApiDto = restTemplate.getForObject(url, EnderecoApiDto.class);
            if (enderecoApiDto == null || enderecoApiDto.getCep() == null) {
                throw new CepNaoExisteException("CEP não existe!");
            }
            return enderecoApiDto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar o serviço de CEP: " + e.getMessage(), e);
        }
    }
}
