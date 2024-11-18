package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.MetaEsportivaDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;

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
    ConquistaRepository conquistaRepository;

    public ModalidadeEsportivaDto criarModalidade(ModalidadeEsportivaDto modalidade) throws ModalidadeJaExisteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByNome(modalidade.nome());

        if (modalidadeExistente.isEmpty()){
            ModalidadeEsportiva novaModalidade = new ModalidadeEsportiva();
            novaModalidade.fromDto(modalidade);
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
            modalidadeEdit.fromDto(modalidade);
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

    public List<ModalidadeEsportivaDto> buscarModalidades(String nome)throws ModalidadeNaoExistenteException {
        List<ModalidadeEsportiva> modalidades = modalidadeEsportivaRepository.findByNomeContainingAndAtivoTrue(nome);

        if (!modalidades.isEmpty()){
            return modalidades.stream().map(
                    ModalidadeEsportivaDto::toDTO
            ).toList();
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!!");
        }
    }

    public ModalidadeEsportivaDto desativarModalidade(Long id) throws ModalidadeNaoExistenteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(id);

        if (modalidadeExistente.isPresent()){
            ModalidadeEsportiva modalidadeDesativada = modalidadeExistente.get();
            modalidadeDesativada.inativar(metaEsportivaRepository, conquistaRepository);
            ModalidadeEsportiva modalidadeEsportiva = modalidadeEsportivaRepository.save(modalidadeDesativada);
            return ModalidadeEsportivaDto.toDTO(modalidadeEsportiva);
        }else {
            throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
        }
    }

    public void inscreverEmModalidade(Long idAcademico, Long idModalidade) throws ModalidadeNaoExistenteException, AcademicoNaoExisteException, Exception {
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findByIdModalidadeEsportivaAndAtivo(idModalidade, true);
        Optional<Academico> academico = academicoRepository.findByIdAcademicoAndUsuarioAtivo(idAcademico, true);
        Optional<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico, idModalidade);

        if (academicoModalidade.isEmpty()) {
            if (academico.isPresent()) {
                if (modalidade.isPresent()) {
                    AcademicoModalidadeEsportiva inscricaoModalidade = new AcademicoModalidadeEsportiva();
                    inscricaoModalidade.setModalidadeEsportiva(modalidade.get());
                    inscricaoModalidade.setAcademico(academico.get());
                    inscricaoModalidade.inscrever(idAcademico, idModalidade, metaEsportivaRepository, conquistaRepository);
                    academicoModalidadeEsportivaRepository.save(inscricaoModalidade);
                } else {
                    throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
                }
            } else {
                throw new AcademicoNaoExisteException("Usuario não encontrado!");
            }
        }else {
            throw new Exception("O academico ja esta cadastrado na modalidade!");
        }
    }

    public List<ModalidadeEsportivaDto> listarModalidadesInscritas(Long idAcademico) throws AcademicoNaoExisteException, RegistroNaoEncontradoException{
        Optional<Academico>academico = academicoRepository.findById(idAcademico);
        if (academico.isPresent()) {
            if (!academico.get().getModalidadeEsportivas().isEmpty()){
                return academico.get().getModalidadeEsportivas().stream().map(
                        ModalidadeEsportivaDto::toDTO).toList();
            }else {
                throw new RegistroNaoEncontradoException("O academico nao esta inscrito em nenhuma modalidade!");
            }
        } else {
            throw new AcademicoNaoExisteException("Usuario não encontrado!");
        }
    }

    public List<ModalidadeEsportivaDto> listarModalidadesOutroUsuario(Long idAcademico) throws AcademicoNaoExisteException, ConteudoPrivadoException {
        Optional<Academico>academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            return academico.get().getModalidadeEsportivas().stream().map(ModalidadeEsportivaDto::toDTO).toList();
        } else {
            throw new AcademicoNaoExisteException("Usuario não encontrado!");
        }
    }

    public void cancelarInscricaoModalidade(Long idAcademico, Long idModalidade) throws AcademicoNaoExisteException, ModalidadeNaoExistenteException, InscricaoEmModalidadeNaoExisteException {
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findByIdModalidadeEsportivaAndAtivo(idModalidade, true);
        Optional<Academico> academico = academicoRepository.findByIdAcademicoAndUsuarioAtivo(idAcademico, true);
        Optional<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico, idModalidade);

        if (academico.isPresent()) {
            if (modalidade.isPresent()) {
                if (academicoModalidade.isPresent()) {
                    academicoModalidade.get().desinscrever(idAcademico, idModalidade, conquistaRepository);
                    academicoModalidadeEsportivaRepository.deleteById(academicoModalidade.get().getIdAcademicoModalidadeEsportiva());
                } else {
                    throw new InscricaoEmModalidadeNaoExisteException("O academico nao esta inscrito na modalidade!");
                }
            } else {
                throw new ModalidadeNaoExistenteException("A modalidade nao existe!");
            }
        } else {
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

    public MetaEsportivaDto adicionarMetaEsportiva(MetaEsportivaDto metaEsportiva) throws TipoInvalidoException, ModalidadeNaoExistenteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByIdModalidadeEsportivaAndAtivo(metaEsportiva.idModalidadeEsportiva(), true);
        Optional<MetaEsportiva> metaEsportivaExistente = metaEsportivaRepository.findByTituloAndAtivo(metaEsportiva.titulo(), true);

        if (modalidadeExistente.isPresent()) {
            if (metaEsportivaExistente.isEmpty()) {
                MetaEsportiva novaMeta = new MetaEsportiva();
                novaMeta.fromDto(metaEsportiva);
                novaMeta.setModalidadeEsportiva(modalidadeExistente.get());
                MetaEsportiva metaCadastrada = metaEsportivaRepository.save(novaMeta);
                metaCadastrada.cadastrarConquistas(academicoModalidadeEsportivaRepository, conquistaRepository);
                return MetaEsportivaDto.toDto(metaCadastrada);
            } else {
                throw new TipoInvalidoException("Modalidade ja cadastrada!");
            }
        } else {
            throw new ModalidadeNaoExistenteException("Modalidade não encontrada!");
        }
    }

    public MetaEsportivaDto atualizarMetaEsportiva(MetaEsportivaDto metaEsportiva, Long idMeta) throws RegistroNaoEncontradoException, ModalidadeNaoExistenteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findByIdModalidadeEsportivaAndAtivo(metaEsportiva.idModalidadeEsportiva(), true);
        Optional<MetaEsportiva> metaEsportivaExistente = metaEsportivaRepository.findByIdMetaEsportivaAndAtivo(metaEsportiva.idMetaEsportiva(), true);

        if (modalidadeExistente.isPresent()) {
            if (metaEsportivaExistente.isPresent()) {
                metaEsportivaExistente.get().atualizar(metaEsportiva);
                return MetaEsportivaDto.toDto(metaEsportivaRepository.save(metaEsportivaExistente.get()));
            } else {
                throw new RegistroNaoEncontradoException("Meta esportiva não encontrada!");
            }
        } else {
            throw new ModalidadeNaoExistenteException("Modalidade não encontrada!");
        }
    }

    public void desativarMetaEsportiva(Long idMeta) throws RegistroNaoEncontradoException {
        Optional<MetaEsportiva> metaEsportivaExistente = metaEsportivaRepository.findById(idMeta);
        if (metaEsportivaExistente.isPresent()){
            metaEsportivaExistente.get().desativarConquistas(idMeta, conquistaRepository);
            metaEsportivaRepository.save(metaEsportivaExistente.get());
        }else {
            throw new RegistroNaoEncontradoException("Modalidade não encontrada!");
        }
    }

    public List<MetaEsportivaDto> listarMetaEsportivaPorModalidade(Long idModalidade) throws RegistroNaoEncontradoException, ModalidadeNaoExistenteException {
        Optional<ModalidadeEsportiva> modalidadeExistente = modalidadeEsportivaRepository.findById(idModalidade);

        if (modalidadeExistente.isPresent()){
            List<MetaEsportiva> metasEsportivas = metaEsportivaRepository.findByModalidadeEsportivaAndAtivo(modalidadeExistente.get(), true);

            if (metasEsportivas.isEmpty()){
                throw new RegistroNaoEncontradoException("Nenhuma meta cadastrada na modalidade!");
            }else {
                return metasEsportivas.stream().map(
                        metaEsportiva -> MetaEsportivaDto.toDto(metaEsportiva)
                ).toList();
            }
        }else {
            throw new ModalidadeNaoExistenteException("Modalidade ja cadastrada!");
        }
    }
}
