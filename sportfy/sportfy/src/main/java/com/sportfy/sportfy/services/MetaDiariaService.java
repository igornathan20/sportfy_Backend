package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.MetaDiariaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.MetaDiariaNaoExistenteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.MetaDiaria;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.MetaDiariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaDiariaService {

    @Autowired
    MetaDiariaRepository metaDiariaRepository;
    @Autowired
    AcademicoRepository academicoRepository;

    public MetaDiaria criarMeta(MetaDiariaDto meta) throws AcademicoNaoExisteException{
        Optional<Academico> existAcademico = academicoRepository.findById(meta.idAcademico());

        if (existAcademico.isPresent()){
            MetaDiaria novaMeta = new MetaDiaria();
            novaMeta.updateFromDto(meta);
            novaMeta.setAcademico(existAcademico.get());
        return metaDiariaRepository.save(novaMeta);
        }else {
            throw new AcademicoNaoExisteException("O Usuario nao existe!");
        }
    }

    public MetaDiaria editarMeta(MetaDiariaDto meta) throws MetaDiariaNaoExistenteException {
        Optional<MetaDiaria> metaExistente = metaDiariaRepository.findById(meta.idMetaDiaria());

        if (metaExistente.isPresent()){
            MetaDiaria metaEdit = metaExistente.get();
            metaEdit.updateFromDto(meta);
            return metaDiariaRepository.save(metaEdit);
        }else {
            throw new MetaDiariaNaoExistenteException("A meta diaria nao existe!");
        }
    }

    public List<MetaDiaria> listarMetas()throws MetaDiariaNaoExistenteException{
        List<MetaDiaria> metasDiarias = metaDiariaRepository.findAll();

        if (!metasDiarias.isEmpty()){
            return metasDiarias;
        }else {
            throw new MetaDiariaNaoExistenteException("Nenhuma meta diaria cadastrada!");
        }
    }

    public List<Optional<MetaDiaria>> buscarMeta(String nome)throws MetaDiariaNaoExistenteException{
        List<Optional<MetaDiaria>> metasDiarias = metaDiariaRepository.findByTituloContainingIgnoreCase(nome);

        if (!metasDiarias.isEmpty()){
            return metasDiarias;
        }else {
            throw new MetaDiariaNaoExistenteException("A modalidade nao existe!!");
        }
    }

    public Optional<MetaDiaria> excluirModalidade(Long id)throws MetaDiariaNaoExistenteException{
        Optional<MetaDiaria> metaDiaria = metaDiariaRepository.findById(id);

        if (metaDiaria.isPresent()){
            metaDiariaRepository.deleteById(id);
            return metaDiaria;
        }else {
            throw new MetaDiariaNaoExistenteException("A modalidade nao existe!");
        }
    }
}