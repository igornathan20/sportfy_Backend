package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.ApoioSaudeDto;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.RegistroNaoEncontradoException;
import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.ApoioSaude;
import com.sportfy.sportfy.repositories.AdministradorRepository;
import com.sportfy.sportfy.repositories.ApoioSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApoioSaudeService {
    @Autowired
    ApoioSaudeRepository apoioSaudeRepository;
    @Autowired
    AdministradorRepository administradorRepository;

    public ApoioSaude criarMeta(ApoioSaudeDto apoioSaudeDto) throws AdministradorNaoExisteException {
        Optional<Administrador> administrador = administradorRepository.findById(apoioSaudeDto.idAdministrador());

        if (administrador.isPresent()){
            ApoioSaude novoRegistro = new ApoioSaude();
            novoRegistro.updateFromDto(apoioSaudeDto);
            novoRegistro.setAdministrador(administrador.get());
            return apoioSaudeRepository.save(novoRegistro);
        }else {
            throw new AdministradorNaoExisteException("O Usuario nao existe!");
        }
    }

    public ApoioSaude editarApoioSaude(ApoioSaudeDto apoioSaudeDto) throws RegistroNaoEncontradoException {
        Optional<ApoioSaude> apoioSaudeExistente = apoioSaudeRepository.findById(apoioSaudeDto.idApoioSaude());

        if (apoioSaudeExistente.isPresent()){
            ApoioSaude apoioSaudeEdit = apoioSaudeExistente.get();
            apoioSaudeEdit.updateFromDto(apoioSaudeDto);
            return apoioSaudeRepository.save(apoioSaudeEdit);
        }else {
            throw new RegistroNaoEncontradoException("O registro de apoio a saude nao existe!");
        }
    }

    public List<ApoioSaude> listar() throws RegistroNaoEncontradoException{
        List<ApoioSaude> registrosApoioSaude = apoioSaudeRepository.findAll();

        if (!registrosApoioSaude.isEmpty()){
            return registrosApoioSaude;
        }else {
            throw new RegistroNaoEncontradoException("Nenhuma registro de apoio a saude cadastrado!");
        }
    }

    public List<Optional<ApoioSaude>> buscarApoioSaude(String nome)throws RegistroNaoEncontradoException{
        List<Optional<ApoioSaude>> metasDiarias = apoioSaudeRepository.findByNomeContainingIgnoreCase(nome);

        if (!metasDiarias.isEmpty()){
            return metasDiarias;
        }else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    public Optional<ApoioSaude> excluirApoioSaude(Long id)throws RegistroNaoEncontradoException{
        Optional<ApoioSaude> ApoioSaude = apoioSaudeRepository.findById(id);

        if (ApoioSaude.isPresent()){
            apoioSaudeRepository.deleteById(id);
            return ApoioSaude;
        }else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }
}
