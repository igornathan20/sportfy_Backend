package com.sportfy.sportfy.services;


import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeJaExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.AcademicoModalidadeEsportiva;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import com.sportfy.sportfy.repositories.AcademicoModalidadeEsportivaReposity;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadeEsportivaService {
    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;
    @Autowired
    AcademicoModalidadeEsportivaReposity academicoModalidadeEsportivaReposity;
    @Autowired
    AcademicoRepository academicoRepository;


    public ModalidadeEsportiva criarModalidade(ModalidadeEsportivaDto modalidade) throws ModalidadeJaExisteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());
        if (modalidadeExistente.isEmpty()){
            ModalidadeEsportiva novaModalidade = new ModalidadeEsportiva();
            novaModalidade.updateFromDto(modalidade);
            novaModalidade.setAtivo(true);
            return modalidadeEsportivaRepository.save(novaModalidade);
        }else {
            throw new ModalidadeJaExisteException("A modalidade ja existe!");
        }
    }

    public ModalidadeEsportiva editarModalidade(ModalidadeEsportivaDto modalidade)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());

        if (modalidadeExistente.isPresent()){
            ModalidadeEsportiva modalidadeEdit = modalidadeExistente.get();
            modalidadeEdit.updateFromDto(modalidade);
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

    public Optional<ModalidadeEsportiva> excluirModalidade(Long id)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(id);

        if (modalidadeExistente.isPresent()){
            modalidadeEsportivaRepository.deleteById(id);
            return modalidadeExistente;
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public ModalidadeEsportiva desativarModalidade(Long id) throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(id);

        if (modalidadeExistente.isPresent()){
            ModalidadeEsportiva modalidadeDesativada = modalidadeExistente.get();
            modalidadeDesativada.setAtivo(false);
            return modalidadeEsportivaRepository.save(modalidadeDesativada);
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public void inscreverEmModalidade(Long idAcademico, Long idModalidade) throws ModalidadeNaoExistenteException, AcademicoNaoExisteException, Exception{
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(idModalidade);
        Optional<Academico>academico = academicoRepository.findById(idAcademico);
        Optional<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaReposity.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico,idModalidade);

        if (academicoModalidade.isEmpty()) {
            if (academico.isPresent()) {
                if (modalidade.isPresent()) {
                    AcademicoModalidadeEsportiva inscricaoModalidade = new AcademicoModalidadeEsportiva();
                    inscricaoModalidade.setModalidadeEsportiva(modalidade.get());
                    inscricaoModalidade.setAcademico(academico.get());
                    academicoModalidadeEsportivaReposity.save(inscricaoModalidade);
                } else {
                    throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
                }
            } else {
                throw new AcademicoNaoExisteException("Usuario não encontrado!");
            }
        }else {
            throw new Exception("O usuario ja esta cadastrado na modalidade!");
        }
    }

    public void cancelarInscricaoModalidade(Long idAcademico, Long idModalidade) throws ModalidadeNaoExistenteException, AcademicoNaoExisteException{
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(idModalidade);
        Optional<Academico>academico = academicoRepository.findById(idAcademico);

        Optional<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaReposity.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico,idModalidade);
        if (academico.isPresent()) {
            if (modalidade.isPresent()) {
                academicoModalidadeEsportivaReposity.deleteById(academicoModalidade.get().getIdAcademicoModalidadeEsportiva());
            } else {
                throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
            }
        }else {
            throw new AcademicoNaoExisteException("Usuario não encontrado!");
        }
    }
}
