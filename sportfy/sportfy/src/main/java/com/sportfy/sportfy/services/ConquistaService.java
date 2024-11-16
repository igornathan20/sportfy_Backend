package com.sportfy.sportfy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.ConquistaDto;
import com.sportfy.sportfy.exeptions.ConquistaNaoExistenteException;
import com.sportfy.sportfy.exeptions.MetaEsportivaNaoExisteException;
import com.sportfy.sportfy.models.Conquista;
import com.sportfy.sportfy.repositories.ConquistaRepository;

@Service
public class ConquistaService {

    @Autowired
    private ConquistaRepository conquistaRepository;

    public ConquistaDto atualizarConquista(ConquistaDto conquistaDto) throws ConquistaNaoExistenteException {
        Conquista conquistaBD = conquistaRepository.findByIdConquistaAndAcademicoIdAcademicoAndAtivo(conquistaDto.idConquista(), conquistaDto.idAcademico(), true);
        if (conquistaBD == null) {
            throw new ConquistaNaoExistenteException("Conquista não existe!");
        }
        Conquista conquistaAtualizar = new Conquista();
        conquistaAtualizar.atualizar(conquistaDto);
        Conquista conquistaAtualizada = conquistaRepository.save(conquistaAtualizar);
        return ConquistaDto.fromConquistaBD(conquistaAtualizada);
    }

    public List<ConquistaDto> listarConquistas(Long idAcademico) throws MetaEsportivaNaoExisteException {
        List<Conquista> listaConquistaBD = conquistaRepository.findByAcademicoIdAcademicoAndAtivo(idAcademico, true);
        List<ConquistaDto> listaConquista = new ArrayList<>();

        if (listaConquistaBD.isEmpty()) {
            throw new MetaEsportivaNaoExisteException("Lista de metas esportivas e conquistas vazia!");
        }

        listaConquistaBD.forEach(conquista -> {
            listaConquista.add(ConquistaDto.fromConquistaBD(conquista));
        });
        return listaConquista;
    }
}
