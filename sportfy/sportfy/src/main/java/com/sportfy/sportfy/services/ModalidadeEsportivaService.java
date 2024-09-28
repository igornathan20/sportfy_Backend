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
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.RegraRepository;

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

    @Autowired
    MetaEsportivaRepository metaEsportivaRepository;

    @Autowired
    RegraRepository regraRepository;

    public ModalidadeEsportivaDto criarModalidade(ModalidadeEsportivaDto modalidade) throws ModalidadeJaExisteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());
        if (modalidadeExistente.isEmpty()){
            ModalidadeEsportiva novaModalidade = new ModalidadeEsportiva();
            novaModalidade.cadastrar(modalidade);
            ModalidadeEsportiva modalidadeEsportiva = modalidadeEsportivaRepository.save(novaModalidade);
            return ModalidadeEsportivaDto.toDTO(modalidadeEsportiva);
        }else {
            throw new ModalidadeJaExisteException("A modalidade ja existe!");
        }
    }

    public ModalidadeEsportivaDto editarModalidade(ModalidadeEsportivaDto modalidade)throws ModalidadeNaoExistenteException, ModalidadeJaExisteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(modalidade.idModalidadeEsportiva());

        if (modalidadeExistente.isPresent()){
            Optional<ModalidadeEsportiva> modalidadeExistenteNome = modalidadeEsportivaRepository.findByNome(modalidade.nome());
            if (modalidadeExistenteNome.isPresent() && modalidadeExistenteNome.get().getIdModalidadeEsportiva() != modalidade.idModalidadeEsportiva()) {
                throw new ModalidadeJaExisteException("Outra modalidade com esse nome já existente!");
                
            }
            ModalidadeEsportiva modalidadeEdit = modalidadeExistente.get();
            modalidadeEdit.atualizar(modalidade.idModalidadeEsportiva(), modalidade, metaEsportivaRepository, regraRepository);
            ModalidadeEsportiva modalidadeEsportiva = modalidadeEsportivaRepository.save(modalidadeEdit);
            return ModalidadeEsportivaDto.toDTO(modalidadeEsportiva);
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public List<ModalidadeEsportivaDto> listarModalidades()throws ModalidadeNaoExistenteException{
        List<ModalidadeEsportiva> modalidades = modalidadeEsportivaRepository.findByAtivoTrueWithActiveMetas();

        if (!modalidades.isEmpty()){
            return modalidades.stream().map(ModalidadeEsportivaDto::toDTO).toList();
        }else {
            throw new ModalidadeNaoExistenteException("Nenhuma modalidade cadastrada!");
        }
    }

    public ModalidadeEsportivaDto buscarModalidades(String nome)throws ModalidadeNaoExistenteException{
        Optional<ModalidadeEsportiva> modalidades = modalidadeEsportivaRepository.findByNomeAndAtivoTrueWithActiveMetas(nome);

        if (!modalidades.isEmpty()){
            return ModalidadeEsportivaDto.toDTO(modalidades.get());
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!!");
        }
    }

    public ModalidadeEsportivaDto desativarModalidade(Long id) throws ModalidadeNaoExistenteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(id);

        if (modalidadeExistente.isPresent()){
            ModalidadeEsportiva modalidadeDesativada = modalidadeExistente.get();
            modalidadeDesativada.setAtivo(false);
            ModalidadeEsportiva modalidadeEsportiva = modalidadeEsportivaRepository.save(modalidadeDesativada);
            return ModalidadeEsportivaDto.toDTO(modalidadeEsportiva);
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
