package com.sportfy.sportfy.services;


import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;
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
    CampeonatoRepository campeonatoRepository;
    @Autowired
    PartidaRepository partidaRepository;
    @Autowired
    PrivacidadeRepository privacidadeRepository;


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

    public List<ModalidadeEsportiva> listarModalidadesInscritas(Long idAcademico) throws AcademicoNaoExisteException{
        Optional<Academico>academico = academicoRepository.findById(idAcademico);
        if (academico.isPresent()) {
            return academico.get().getModalidadeEsportivas();
        } else {
            throw new AcademicoNaoExisteException("Usuario não encontrado!");
        }
    }

    public List<ModalidadeEsportiva> listarModalidadesOutroUsuario(Long idAcademico) throws AcademicoNaoExisteException, ConteudoPrivadoException{
        Optional<Academico>academico = academicoRepository.findById(idAcademico);
        Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);

        if (academico.isPresent()) {
            if (privacidade.isMostrarModalidadesEsportivas()){
                return academico.get().getModalidadeEsportivas();
            }else {
                throw new ConteudoPrivadoException("O usuario definiu como privado a visualizacao das modalidades!");
            }
        } else {
            throw new AcademicoNaoExisteException("Usuario não encontrado!");
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

    public EstatisticasGeraisModalidadeDto estatisticasGeraisPorModalidade(Long idModalidade)throws  ModalidadeNaoExistenteException, RegistroNaoEncontradoException {
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);
        List<AcademicoModalidadeEsportiva> inscritosModalidade = academicoModalidadeEsportivaReposity.findByModalidadeEsportiva(modalidadeEsportiva.get());

        if (modalidadeEsportiva.isPresent()){
            List<Campeonato> campeonatos = campeonatoRepository.findByModalidadeEsportiva(modalidadeEsportiva.get());
            int numeroDeCampeonatos = campeonatos.size();
            int totalPartidas = 0;

            if (campeonatos.isEmpty()){
                throw new RegistroNaoEncontradoException("essa modalidade nao possui nenhum campeonato!");
            }else {
                for (int i = 0; i < campeonatos.size(); i++){
                    List<Partida> partidasModalidades = partidaRepository.findByCampeonato(campeonatos.get(i));
                    totalPartidas += partidasModalidades.size();
                }
                EstatisticasGeraisModalidadeDto estatisticas = new EstatisticasGeraisModalidadeDto(modalidadeEsportiva.get().getNome(), numeroDeCampeonatos, totalPartidas, inscritosModalidade.size());
                return estatisticas;
            }
        }else {
            throw new ModalidadeNaoExistenteException("Modalidade nao encontrada!");
        }
    }
}
