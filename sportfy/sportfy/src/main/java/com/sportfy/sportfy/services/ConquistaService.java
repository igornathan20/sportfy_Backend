package com.sportfy.sportfy.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.ConquistaDto;
import com.sportfy.sportfy.exeptions.ConquistaJaExistenteException;
import com.sportfy.sportfy.exeptions.ConquistaNaoExistenteException;
import com.sportfy.sportfy.models.Conquista;
import com.sportfy.sportfy.repositories.ConquistaRepository;

@Service
public class ConquistaService {
    @Autowired
    private ConquistaRepository conquistaRepository;

    public ConquistaDto conquistar(Long idAcademico, Long idMetaEsportiva) throws ConquistaJaExistenteException {
        Optional<Conquista> conquistaBD = conquistaRepository.findByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportiva(idAcademico, idMetaEsportiva);
        if (!conquistaBD.isEmpty()) {
            throw new ConquistaJaExistenteException("Conquista já existente!");
        }
        Conquista novaConquista = new Conquista();
        novaConquista.cadastrar(idAcademico, idMetaEsportiva);
        Conquista conquistaCriada = conquistaRepository.save(novaConquista);
        return ConquistaDto.fromConquistaBD(conquistaCriada);
    }

    public List<ConquistaDto> listarConquistas(Long idAcademico) throws ConquistaNaoExistenteException {
        List<Conquista> listaConquistaBD = conquistaRepository.findByAcademicoIdAcademico(idAcademico);
        if (listaConquistaBD.isEmpty()) {
            throw new ConquistaNaoExistenteException("Lista de conquista vazia!");
        }
        List<ConquistaDto> listaConquistaDto = listaConquistaBD.stream().map(conquistaBD -> ConquistaDto.fromConquistaBD(conquistaBD)).collect(Collectors.toList());
        return listaConquistaDto;
    }
}
