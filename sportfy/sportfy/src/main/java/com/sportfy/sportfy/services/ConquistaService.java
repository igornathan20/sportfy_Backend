package com.sportfy.sportfy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.ConquistaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ConquistaNaoExistenteException;
import com.sportfy.sportfy.exeptions.ConteudoPrivadoException;
import com.sportfy.sportfy.exeptions.MetaEsportivaNaoExisteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Conquista;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.ConquistaRepository;
import com.sportfy.sportfy.repositories.PrivacidadeRepository;

@Service
public class ConquistaService {

    @Autowired
    private ConquistaRepository conquistaRepository;

    @Autowired
    private AcademicoRepository academicoRepository;

    @Autowired
    private PrivacidadeRepository privacidadeRepository;

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

        listaConquistaBD.forEach(conquista -> {listaConquista.add(ConquistaDto.fromConquistaBD(conquista));});
        return listaConquista;
    }

    public List<ConquistaDto> listarConquistasOutroAcademico(Long idAcademico) throws AcademicoNaoExisteException, ConteudoPrivadoException, MetaEsportivaNaoExisteException {
        List<ConquistaDto> listaConquista = new ArrayList<>();
        Optional<Academico> academicoBD = academicoRepository.findById(idAcademico);
        if (academicoBD.isEmpty()) {
            throw new AcademicoNaoExisteException("Acadêmico não existe!");
        }

        boolean mostrarConquistas = privacidadeRepository.findByIdAcademico(academicoBD.get().getIdAcademico()).isMostrarConquistas();
        if (!mostrarConquistas) {
            throw new ConteudoPrivadoException("Acadêmico não permite mostrar conquistas!");
        }

        List<Conquista> listaConquistaBD = conquistaRepository.findByAcademicoIdAcademicoAndAtivo(idAcademico, true);
        if (listaConquistaBD.isEmpty()) {
            throw new MetaEsportivaNaoExisteException("Lista de metas esportivas e conquistas vazia!");
        }

        listaConquistaBD.forEach(conquista -> {listaConquista.add(ConquistaDto.fromConquistaBD(conquista));});
        return listaConquista;
    }
}
