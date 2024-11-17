package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.ApoioSaudeDto;
import com.sportfy.sportfy.dtos.ApoioSaudeResponseDto;
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
import java.util.stream.Collectors;

@Service
public class ApoioSaudeService {
    @Autowired
    ApoioSaudeRepository apoioSaudeRepository;
    @Autowired
    AdministradorRepository administradorRepository;

    public ApoioSaudeResponseDto criarApoioSaude(ApoioSaudeDto apoioSaudeDto) throws AdministradorNaoExisteException {
        Optional<Administrador> administrador = administradorRepository.findById(apoioSaudeDto.idAdministrador());

        if (administrador.isPresent()){
            ApoioSaude novoRegistro = new ApoioSaude();
            novoRegistro.fromDto(apoioSaudeDto);
            novoRegistro.setAdministrador(administrador.get());
            novoRegistro.setAtivo(true);
            return ApoioSaude.toDto(apoioSaudeRepository.save(novoRegistro));
        }else {
            throw new AdministradorNaoExisteException("O Usuario nao existe!");
        }
    }

    public ApoioSaudeResponseDto editarApoioSaude(Long idApoioSaude, ApoioSaudeDto apoioSaudeDto) throws RegistroNaoEncontradoException {
        Optional<ApoioSaude> apoioSaudeExistente = apoioSaudeRepository.findById(idApoioSaude);

        if (apoioSaudeExistente.isPresent()){
            ApoioSaude apoioSaudeEdit = apoioSaudeExistente.get();
            apoioSaudeEdit.fromDto(apoioSaudeDto);
            return ApoioSaude.toDto(apoioSaudeRepository.save(apoioSaudeEdit));
        }else {
            throw new RegistroNaoEncontradoException("O registro de apoio a saude nao existe!");
        }
    }

    public List<ApoioSaudeResponseDto> listar() throws RegistroNaoEncontradoException{
        List<ApoioSaudeResponseDto> registrosApoioSaude = apoioSaudeRepository.findAll().stream()
                .map(ApoioSaude::toDto).collect(Collectors.toList());;

        if (!registrosApoioSaude.isEmpty()){
            return registrosApoioSaude;
        }else {
            throw new RegistroNaoEncontradoException("Nenhuma registro de apoio a saude cadastrado!");
        }
    }

    public List<ApoioSaudeResponseDto> buscarApoioSaude(String nome)throws RegistroNaoEncontradoException{
        List<ApoioSaudeResponseDto> apoioSaude = apoioSaudeRepository.findByNomeContainingIgnoreCase(nome).stream().map(ApoioSaude::toDto)
                .collect(Collectors.toList());;

        if (!apoioSaude.isEmpty()){
            return apoioSaude;
        }else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    public ApoioSaudeResponseDto excluirApoioSaude(Long id)throws RegistroNaoEncontradoException{
        Optional<ApoioSaude> apoioSaude =  apoioSaudeRepository.findById(id);

        if (apoioSaude.isPresent()){
            apoioSaudeRepository.deleteById(id);
            return ApoioSaude.toDto(apoioSaude.get());
        }else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    public ApoioSaudeResponseDto desativarApoioSaude(Long id) throws RegistroNaoEncontradoException {
        Optional<ApoioSaude> apoioSaude = apoioSaudeRepository.findById(id);

        if (apoioSaude.isPresent()) {
            ApoioSaude apoioSaudeDesativado = apoioSaude.get();
            apoioSaudeDesativado.setAtivo(false);
            apoioSaude.get();
            return ApoioSaude.toDto(apoioSaudeRepository.save(apoioSaudeDesativado));
        } else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }
}
