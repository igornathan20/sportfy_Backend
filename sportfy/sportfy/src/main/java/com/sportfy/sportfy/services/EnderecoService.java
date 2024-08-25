package com.sportfy.sportfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.EnderecoApiDto;
import com.sportfy.sportfy.exeptions.CepInvalidoException;
import com.sportfy.sportfy.exeptions.CepNaoExisteException;
import com.sportfy.sportfy.util.ConsultarEndereco;

@Service
public class EnderecoService {

    @Autowired
    private ConsultarEndereco consultarEndereco;

    public EnderecoApiDto consultar(String cep) throws CepNaoExisteException, CepInvalidoException {
        return consultarEndereco.consultarEndereco(cep);
    }
}
