package com.sportfy.sportfy.services;


import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.ModalidadeJaExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadeEsportivaService {
    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;

    public ModalidadeEsportiva criarModalidade(ModalidadeEsportivaDto modalidade) throws ModalidadeJaExisteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());

        if (modalidadeExistente.isEmpty()){
            ModalidadeEsportiva novaModalidade = new ModalidadeEsportiva();
            novaModalidade.toEntity(modalidade);
            return modalidadeEsportivaRepository.save(novaModalidade);
        }else {
            throw new ModalidadeJaExisteException("A modalidade ja existe!");
        }
    }

    public ModalidadeEsportiva editarModalidade(ModalidadeEsportivaDto modalidade)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());

        if (modalidadeExistente.isPresent()){
            ModalidadeEsportiva modalidadeEdit = new ModalidadeEsportiva();
            modalidadeEdit.setIdModalidadeEsportiva(modalidade.idModalidadeEsportiva());
            modalidadeEdit.toEntity(modalidade);
            return modalidadeEsportivaRepository.save(modalidadeEdit);
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public List<ModalidadeEsportiva> listarModalidades()throws ModalidadeNaoExistenteException{
        List<ModalidadeEsportiva> modalidades = modalidadeEsportivaRepository.findAll();

        if (!modalidades.isEmpty()){
            return modalidades;
        }else {
            throw new ModalidadeNaoExistenteException("Nenhuma modalidade cadastrada!");
        }
    }

    public Optional<ModalidadeEsportiva> buscarModalidades(String nome)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidades = modalidadeEsportivaRepository.findByNome(nome);

        if (!modalidades.isEmpty()){
            return modalidades;
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!!");
        }
    }

    public Optional<ModalidadeEsportiva> removerModalidade(Long id)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(id);

        if (modalidadeExistente.isPresent()){
            modalidadeEsportivaRepository.deleteById(id);
            return modalidadeExistente;
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public ModalidadeEsportiva desativarModalidade(Long id)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(id);

        if (modalidadeExistente.isPresent()){
            ModalidadeEsportiva modalidadeDesativada = modalidadeExistente.get();
            modalidadeDesativada.setAtivo(false);
            return modalidadeEsportivaRepository.save(modalidadeDesativada);
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }
}
