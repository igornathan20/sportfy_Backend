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
import java.util.stream.Collectors;

@Service
public class MetaDiariaService {

    @Autowired
    MetaDiariaRepository metaDiariaRepository;
    @Autowired
    AcademicoRepository academicoRepository;

    public MetaDiariaDto criarMeta(MetaDiariaDto meta) throws AcademicoNaoExisteException{
        Optional<Academico> existAcademico = academicoRepository.findById(meta.idAcademico());

        if (existAcademico.isPresent()) {
            MetaDiaria novaMeta = new MetaDiaria();
            novaMeta.updateFromDto(meta);
            novaMeta.setAcademico(existAcademico.get());
        return MetaDiaria.toDto(metaDiariaRepository.save(novaMeta));
        }else {
            throw new AcademicoNaoExisteException("O Usuario nao existe!");
        }
    }

    public MetaDiariaDto editarMeta(MetaDiariaDto meta) throws MetaDiariaNaoExistenteException {
        Optional<MetaDiaria> metaExistente = metaDiariaRepository.findById(meta.idMetaDiaria());

        if (metaExistente.isPresent()) {
            MetaDiaria metaEdit = metaExistente.get();
            metaEdit.updateFromDto(meta);
            return MetaDiaria.toDto(metaDiariaRepository.save(metaEdit));
        }else {
            throw new MetaDiariaNaoExistenteException("A meta diaria nao existe!");
        }
    }

    public List<MetaDiariaDto> listarMetas(Long idAcademico)throws MetaDiariaNaoExistenteException, AcademicoNaoExisteException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            List<MetaDiariaDto> metasDiarias = metaDiariaRepository.findByAcademico(academico.get()).stream().map(
                    metaDiaria -> MetaDiaria.toDto(metaDiaria)).collect(Collectors.toList()
            );

            if (!metasDiarias.isEmpty()) {
                return metasDiarias;
            } else {
                throw new MetaDiariaNaoExistenteException("Nenhuma meta diaria cadastrada!");
            }
        }else {
            throw new AcademicoNaoExisteException("Acadêmico não encontrado!");
        }
    }

    public List<MetaDiariaDto> buscarMeta(Long idAcademico, String nome)throws MetaDiariaNaoExistenteException, AcademicoNaoExisteException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            List<MetaDiariaDto> metasDiarias = metaDiariaRepository.findByTituloContainingIgnoreCaseAndAcademico(nome, academico.get()).stream().map(
                    metaDiaria -> MetaDiaria.toDto(metaDiaria)).collect(Collectors.toList());

            if (!metasDiarias.isEmpty()) {
                return metasDiarias;
            } else {
                throw new MetaDiariaNaoExistenteException("A modalidade nao existe!");
            }
        }else {
            throw new AcademicoNaoExisteException("Acadêmico não encontrado!");
        }
    }

    public MetaDiariaDto excluirMeta(Long id)throws MetaDiariaNaoExistenteException{
        Optional<MetaDiaria> metaDiaria = metaDiariaRepository.findById(id);

        if (metaDiaria.isPresent()){
            metaDiariaRepository.deleteById(id);
            return MetaDiaria.toDto(metaDiaria.get());
        }else {
            throw new MetaDiariaNaoExistenteException("A modalidade nao existe!");
        }
    }
}
