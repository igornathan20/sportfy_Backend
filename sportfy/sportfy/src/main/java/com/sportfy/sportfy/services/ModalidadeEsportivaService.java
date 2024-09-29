package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeJaExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.AcademicoModalidadeEsportiva;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import com.sportfy.sportfy.repositories.AcademicoModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.RegraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModalidadeEsportivaService {
    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;

    @Autowired
    AcademicoModalidadeEsportivaRepository academicoModalidadeEsportivaRepository;

    @Autowired
    AcademicoRepository academicoRepository;
    @Autowired
    CampeonatoRepository campeonatoRepository;
    @Autowired
    PartidaRepository partidaRepository;
    @Autowired
    PrivacidadeRepository privacidadeRepository;

    @Autowired
    MetaEsportivaRepository metaEsportivaRepository;

    @Autowired
    RegraRepository regraRepository;

    public ModalidadeEsportivaDto criarModalidade(ModalidadeEsportivaDto modalidade) throws ModalidadeJaExisteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());

        if (modalidadeExistente.isEmpty()){
            ModalidadeEsportiva novaModalidade = new ModalidadeEsportiva();
            novaModalidade.updateFromDto(modalidade);
            novaModalidade.setAtivo(true);
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

    public List<ModalidadeEsportivaDto> listarModalidades() throws ModalidadeNaoExistenteException {
        List<ModalidadeEsportiva> modalidades = modalidadeEsportivaRepository.findByAtivoTrueWithActiveMetas();

        if (!modalidades.isEmpty()){
            return modalidades.stream().map(ModalidadeEsportivaDto::toDTO).toList();
        }else {
            throw new ModalidadeNaoExistenteException("Nenhuma modalidade cadastrada!");
        }
    }

    public List<ModalidadeEsportivaDto> listarModalidadesAcademico(Long idAcademico) throws ModalidadeNaoExistenteException {
        List<AcademicoModalidadeEsportiva> listaAcademicoModalidadeEsportivaBD = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaAtivo(idAcademico, true);
        if (listaAcademicoModalidadeEsportivaBD.isEmpty()) {
            throw new ModalidadeNaoExistenteException("Lista de modalidades esportivas vazia!");
        }
        List<ModalidadeEsportivaDto> listaModalidadeEsportivaDto = listaAcademicoModalidadeEsportivaBD.stream().map(academicoModalidadeEsportivaBD -> ModalidadeEsportivaDto.toDTO(academicoModalidadeEsportivaBD.getModalidadeEsportiva())).collect(Collectors.toList());
        return listaModalidadeEsportivaDto;
    }

    public ModalidadeEsportivaDto buscarModalidades(String nome)throws ModalidadeNaoExistenteException {
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
            modalidadeDesativada.inativar(metaEsportivaRepository, regraRepository);
            ModalidadeEsportiva modalidadeEsportiva = modalidadeEsportivaRepository.save(modalidadeDesativada);
            return ModalidadeEsportivaDto.toDTO(modalidadeEsportiva);
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public void inscreverEmModalidade(Long idAcademico, Long idModalidade) throws ModalidadeNaoExistenteException, AcademicoNaoExisteException, Exception {
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(idModalidade);
        Optional<Academico>academico = academicoRepository.findById(idAcademico);
        Optional<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico,idModalidade);

        if (academicoModalidade.isEmpty()) {
            if (academico.isPresent()) {
                if (modalidade.isPresent()) {
                    AcademicoModalidadeEsportiva inscricaoModalidade = new AcademicoModalidadeEsportiva();
                    inscricaoModalidade.setModalidadeEsportiva(modalidade.get());
                    inscricaoModalidade.setAcademico(academico.get());
                    academicoModalidadeEsportivaRepository.save(inscricaoModalidade);
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

    public void cancelarInscricaoModalidade(Long idAcademico, Long idModalidade) throws ModalidadeNaoExistenteException, AcademicoNaoExisteException {
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(idModalidade);
        Optional<Academico>academico = academicoRepository.findById(idAcademico);

        Optional<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico,idModalidade);
        if (academico.isPresent()) {
            if (modalidade.isPresent()) {
                academicoModalidadeEsportivaRepository.deleteById(academicoModalidade.get().getIdAcademicoModalidadeEsportiva());
            } else {
                throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
            }
        }else {
            throw new AcademicoNaoExisteException("Usuario não encontrado!");
        }
    }

    public EstatisticasGeraisModalidadeDto estatisticasGeraisPorModalidade(Long idModalidade)throws  ModalidadeNaoExistenteException, RegistroNaoEncontradoException {
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);
        List<AcademicoModalidadeEsportiva> inscritosModalidade = academicoModalidadeEsportivaRepository.findByModalidadeEsportiva(modalidadeEsportiva.get());

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
