package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.CampeonatoDto;
import com.sportfy.sportfy.dtos.TimeDto;
import com.sportfy.sportfy.enums.TipoFasePartida;
import com.sportfy.sportfy.enums.TipoSituacao;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CampeonatoService {
    @Autowired
    CampeonatoRepository campeonatoRepository;
    @Autowired
    AcademicoRepository academicoRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;
    @Autowired
    TimeRepository timeRepository;
    @Autowired
    JogadorRepository jogadorRepository;
    @Autowired
    PartidaRepository partidaRepository;
    @Autowired
    private PrivacidadeRepository privacidadeRepository;

    public Campeonato criarCampeonato(CampeonatoDto campeonatoDto) throws AcademicoNaoExisteException, ModalidadeNaoExistenteException {
        Optional<Academico> academico = academicoRepository.findById(campeonatoDto.idAcademico());
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(campeonatoDto.idModalidadeEsportiva());

        if (academico.isPresent()) {
            if (modalidade.isPresent()) {

                Campeonato novoCampeonato = new Campeonato();
                try {
                    novoCampeonato.toEntity(campeonatoDto);
                    novoCampeonato.setAcademico(academico.get());
                    novoCampeonato.setModalidadeEsportiva(modalidade.get());
                    novoCampeonato.setCodigo("#" + gerarCodigoUnico());
                    novoCampeonato.setSituacaoCampeonato(TipoSituacao.EM_ABERTO);
                    Endereco enderecoCampeonato = new Endereco();
                    enderecoCampeonato.toEntity(campeonatoDto.endereco());
                    novoCampeonato.setEndereco(enderecoCampeonato);
                    return campeonatoRepository.save(novoCampeonato);
                } catch (Exception e) {
                    return null;
                }
            } else {
                throw new ModalidadeNaoExistenteException("Modalidade esportiva nao cadastrada!");
            }
        } else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }

    public Campeonato editarCampeonato(CampeonatoDto campeonatoDto) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(campeonatoDto.idCampeonato());

        if (campeonato.isPresent()) {
            Campeonato editCampeonato = campeonato.get();
            try {
                editCampeonato.toEntity(campeonatoDto);
                Endereco enderecoCampeonato = editCampeonato.getEndereco();
                enderecoCampeonato.toEntity(campeonatoDto.endereco());
                editCampeonato.setEndereco(enderecoCampeonato);

                return campeonatoRepository.save(editCampeonato);
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new RegistroNaoEncontradoException("Campeonato não encontrado!");
        }
    }

    public List<Campeonato> listarTodosCampeonatos() throws RegistroNaoEncontradoException {
        List<Campeonato> campeonatos = campeonatoRepository.findAll();

        if (campeonatos.isEmpty()) {
            throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
        } else {
            return campeonatos;
        }
    }

    public List<Campeonato> listarCampeonatosComFiltro(CampeonatoDto campeonatoDto) throws RegistroNaoEncontradoException {
        Specification<Campeonato> spec = Specification.where(null);

        if (campeonatoDto.codigo() != null && !campeonatoDto.codigo().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigo"), campeonatoDto.codigo()));
        }

        if (campeonatoDto.titulo() != null && !campeonatoDto.titulo().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + campeonatoDto.titulo().toLowerCase() + "%"));
        }

        if (campeonatoDto.dataInicio() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("dataInicio"), campeonatoDto.dataInicio()));
        }

        if (campeonatoDto.dataFim() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("dataFim"), campeonatoDto.dataFim()));
        }

        if (campeonatoDto.ativo()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("ativo"), campeonatoDto.ativo()));
        }

        if (campeonatoDto.privacidadeCampeonato() != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("privacidadeCampeonato"), campeonatoDto.privacidadeCampeonato()));
        }

        if (campeonatoDto.idAcademico() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idAcademico"), campeonatoDto.idAcademico()));
        }

        if (campeonatoDto.idModalidadeEsportiva() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idModalidadeEsportiva"), campeonatoDto.idModalidadeEsportiva()));
        }

        if (campeonatoDto.situacaoCampeonato() != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("situacaoCampeonato"), campeonatoDto.situacaoCampeonato()));
        }

        List<Campeonato> campeonatos = campeonatoRepository.findAll(spec);

        if (campeonatos.isEmpty()) {
            throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
        }
        return campeonatos;
    }

    public List<Campeonato> listarCampeonatosInscritos(Long idAcademico) throws RegistroNaoEncontradoException, AcademicoNaoExisteException {
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
            List<Campeonato> campeonatos = new ArrayList<Campeonato>();

            for (int i = 0; i < participacoesCampeonatos.size(); i++){
                campeonatos.add(participacoesCampeonatos.get(i).getTime().getCampeonato());
            }

            if (campeonatos.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
            }
            return campeonatos;
        } else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }

    public List<Campeonato> listarCampeonatosCriados(Long idAcademico) throws RegistroNaoEncontradoException, AcademicoNaoExisteException {
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            List<Campeonato> campeonatosCriados = campeonatoRepository.findByAcademico(academico.get());

            if (campeonatosCriados.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
            }
            return campeonatosCriados;
        } else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }


    public Optional<Campeonato> excluirCampeonato(Long id) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);

        if (campeonato.isPresent()) {
            campeonatoRepository.deleteById(id);
            return campeonato;
        } else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    public Campeonato desativarCampeonato(Long id) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);

        if (campeonato.isPresent()) {
            Campeonato campeonatoDesativado = campeonato.get();
            campeonatoDesativado.setAtivo(false);
            return campeonatoRepository.save(campeonatoDesativado);
        } else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    private String gerarCodigoUnico() {
        String codigo;
        Random random = new Random();
        boolean codigoExiste;
        do {
            codigo = gerarCodigoAleatorio(random);
            codigoExiste = campeonatoRepository.existsByCodigo(codigo);
        } while (codigoExiste);
        return codigo;
    }

    private String gerarCodigoAleatorio(Random random) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            codigo.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return codigo.toString();
    }

    public Time criarTime(TimeDto novoTime) throws CampeonatoInvalidoException, TimeInvalidoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(novoTime.campeonato());
        Optional<Time> timeEncontrado = timeRepository.findByNomeAndCampeonato(novoTime.nome(), campeonato.get());

        if (timeEncontrado.isEmpty()) {
            if (campeonato.get().getDataFim().isAfter(OffsetDateTime.now()) && campeonato.get().getSituacaoCampeonato() != TipoSituacao.FINALIZADO && campeonato.get().getSituacaoCampeonato() != TipoSituacao.INICIADO) {
                Time timeCriado = new Time();
                timeCriado.setNome(novoTime.nome());
                timeCriado.setCampeonato(campeonato.get());
                return timeRepository.save(timeCriado);
            } else {
                throw new CampeonatoInvalidoException("O campeonato ja esta finalizado!");
            }
        } else{
            throw new TimeInvalidoException("Um time com esse nome ja esta cadastrado!");
        }
    }

    public Jogador adicionarJogadorTime(TimeDto timeDto, Long idAcademico) throws CampeonatoInvalidoException, TimeInvalidoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(timeDto.campeonato());
        Optional<Time> timeEncontrado = timeRepository.findByNomeAndCampeonato(timeDto.nome(), campeonato.get());
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (timeEncontrado.isPresent()) {
            if (campeonato.get().getDataFim().isAfter(OffsetDateTime.now()) && campeonato.get().getSituacaoCampeonato() != TipoSituacao.FINALIZADO  && campeonato.get().getSituacaoCampeonato() != TipoSituacao.INICIADO) {
                Jogador novoJogador = new Jogador();
                novoJogador.setModalidadeEsportiva(campeonato.get().getModalidadeEsportiva());
                novoJogador.setAcademico(academico.get());
                novoJogador.setTime(timeEncontrado.get());
                return jogadorRepository.save(novoJogador);
            } else {
                throw new CampeonatoInvalidoException("O campeonato ja esta finalizado!");
            }
        } else{
            throw new TimeInvalidoException("Um time com esse nome ja esta cadastrado!");
        }
    }

    public Time criarTimeComUmJogador(Long idCampeonato, Long idAcademico) throws CampeonatoInvalidoException, TimeInvalidoException, RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (campeonato.isPresent() && academico.isPresent()){
            Optional<Time> timeEncontrado = timeRepository.findByNomeAndCampeonato(academico.get().getUsuario().getUsername(), campeonato.get());
            if (timeEncontrado.isEmpty()) {
                if (campeonato.get().getDataFim().isAfter(OffsetDateTime.now()) && campeonato.get().getSituacaoCampeonato() != TipoSituacao.FINALIZADO && campeonato.get().getLimiteParticipantes() == 1) {
                    Time timeCriado = new Time();
                    timeCriado.setNome(academico.get().getUsuario().getUsername());
                    timeCriado.setCampeonato(campeonato.get());

                    Jogador novoJogador = new Jogador();
                    novoJogador.setModalidadeEsportiva(campeonato.get().getModalidadeEsportiva());
                    novoJogador.setAcademico(academico.get());
                    novoJogador.setTime(timeCriado);
                    jogadorRepository.save(novoJogador);
                    return timeCriado;
                } else {
                    throw new CampeonatoInvalidoException("O campeonato ja esta finalizado!");
                }
            } else{
                throw new TimeInvalidoException("Jogador ja cadastrado no campeonato!");
            }
        }else {
            throw new RegistroNaoEncontradoException("Campeonato ou academico invalido!");
        }
    }

    public List<Partida> definirPrimeiraFase(Long idCampeonato) throws RegistroNaoEncontradoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        if (campeonato.isPresent()){
            List<Time> times = timeRepository.findByCampeonato(campeonato.get());

            int numeroDeTimes = times.size();
            int numeroMaximoTimes = calcularProximaPotenciaDeDois(numeroDeTimes);
            switch (numeroMaximoTimes){
                case 2:
                    campeonato.get().setFaseAtual(TipoFasePartida.FINAL);
                    break;
                case 4:
                    campeonato.get().setFaseAtual(TipoFasePartida.SEMI);
                    break;
                case 8:
                    campeonato.get().setFaseAtual(TipoFasePartida.QUARTAS);
                    break;
                case 16:
                    campeonato.get().setFaseAtual(TipoFasePartida.OITAVAS);
                    break;
                default:
                    //numero maximo de times excedido
            }

            while (times.size() < numeroMaximoTimes) {
                times.add(null);
            }

            embaralharTimes(times);
            List<Partida> partidas = new ArrayList<>();

            for (int i = 0; i < times.size(); i += 2) {
                Time time1 = times.get(i);
                Time time2 = times.get(i + 1);

                Partida partida = new Partida();
                Resultado resultado = new Resultado();
                partida.setCampeonato(campeonato.get());
                partida.setTime1(time1);
                partida.setTime2(time2);
                partida.setFasePartida(campeonato.get().getFaseAtual());
                partida.setResultado(resultado);
                partidas.add(partida);
            }

            //campeonato.setPartidas(partidas);
            partidaRepository.saveAll(partidas);
            campeonato.get().setSituacaoCampeonato(TipoSituacao.INICIADO);
            campeonatoRepository.save(campeonato.get());
            return partidas;
        }else {
            throw new RegistroNaoEncontradoException("Campeonato nao encontrado!");
        }

    }

    private int calcularProximaPotenciaDeDois(int numero) {
        return (int) Math.pow(2, Math.ceil(Math.log(numero) / Math.log(2)));
    }

    private void embaralharTimes(List<Time> times) {
        Random random = new Random();
        for (int i = times.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Time temp = times.get(i);
            times.set(i, times.get(j));
            times.set(j, temp);
        }
    }

    public List<Partida> listarPartidas(Long idCampeonato)throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        if (campeonato.isPresent()) {
            List<Partida> partidas = partidaRepository.findByCampeonato(campeonato.get());
            if(partidas.isEmpty()){
                throw new RegistroNaoEncontradoException("Nenhuma partida foi encontrada no campeonato!");
            }else {
                return partidas;
            }
        }else {
            throw new RegistroNaoEncontradoException("Campeonato nao encontrado!");
        }
    }


    public void avacarDeFase(Long idCampeonato) throws RegistroNaoEncontradoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        if (campeonato.isPresent()) {
            List<Partida> partidasFaseAnterior = partidaRepository.findByCampeonatoAndFasePartida(campeonato.get(), campeonato.get().getFaseAtual());
            List<Time> times = new ArrayList<>();

            for (int i = 0; i < partidasFaseAnterior.size(); i++) {
                if (partidasFaseAnterior.get(i).getTime1() == null) {
                    partidasFaseAnterior.get(i).getResultado().setVencedor(partidasFaseAnterior.get(i).getTime2());
                }
                if (partidasFaseAnterior.get(i).getTime2() == null) {
                    partidasFaseAnterior.get(i).getResultado().setVencedor(partidasFaseAnterior.get(i).getTime1());
                }
                times.add(partidasFaseAnterior.get(i).getResultado().getVencedor());
                partidasFaseAnterior.get(i).setSituacaoPartida(TipoSituacao.FINALIZADO);
            }
            int numeroDeTimes = times.size();

            switch (campeonato.get().getFaseAtual()) {
                case TipoFasePartida.OITAVAS:
                    campeonato.get().setFaseAtual(TipoFasePartida.QUARTAS);
                    break;
                case TipoFasePartida.QUARTAS:
                    campeonato.get().setFaseAtual(TipoFasePartida.SEMI);
                    break;
                case TipoFasePartida.SEMI:
                    campeonato.get().setFaseAtual(TipoFasePartida.FINAL);
                    break;
                default:
                    //fase invalida
            }

            List<Partida> partidas = new ArrayList<>();

            for (int i = 0; i < times.size(); i += 2) {
                Time time1 = times.get(i);
                Time time2 = times.get(i + 1);

                Partida partida = new Partida();
                partida.setCampeonato(campeonato.get());
                partida.setTime1(time1);
                partida.setTime2(time2);
                partida.setFasePartida(campeonato.get().getFaseAtual());
                partidas.add(partida);
            }

            //campeonato.setPartidas(partidas);
            partidaRepository.saveAll(partidas);
        }else {
            throw new RegistroNaoEncontradoException("Campeonato nao encontrado!");
        }
    }

    public Partida alterarPontuacaoPartida(Long idPartida, int pontuacaoTime1, int pontuacaoTime2) throws RegistroNaoEncontradoException{
        Optional<Partida> partida = partidaRepository.findById(idPartida);

        if (partida.isPresent()){
            partida.get().setSituacaoPartida(TipoSituacao.EM_ABERTO);
            partida.get().getResultado().setPontuacaoTime1(pontuacaoTime1);
            partida.get().getResultado().setPontuacaoTime2(pontuacaoTime2);

            if (partida.get().getResultado().getPontuacaoTime1() > partida.get().getResultado().getPontuacaoTime2()){
                partida.get().getResultado().setVencedor(partida.get().getTime1());
            }else {
                partida.get().getResultado().setVencedor(partida.get().getTime2());
            }
            return  partidaRepository.save(partida.get());
        }
        throw new RegistroNaoEncontradoException("nao foi encontrado registro da partida!");
    }

    public AvaliacaoJogador avaliarJogador (Long idAvaliador, Long idJogador, int nota )throws AcademicoNaoExisteException, RegistroNaoEncontradoException{
        Optional<Academico> avaliador = academicoRepository.findById(idAvaliador);
        Optional<Jogador> jogador = jogadorRepository.findById(idJogador);

        if(avaliador.isPresent()){
            if (jogador.isPresent()){
                AvaliacaoJogador avaliacao = new AvaliacaoJogador();
                avaliacao.setJogador(jogador.get());
                avaliacao.setAvaliador(avaliador.get());
                avaliacao.setNota(nota);

                jogador.get().getAvaliacoes().add(avaliacao);
                jogadorRepository.save(jogador.get());

                return avaliacao;
            }else {
                throw new RegistroNaoEncontradoException("Jogador não existente!");
            }
        }else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }

    public float recuperaAvaliacaoNoCampeonato(Long idCampeonato, Long idAcademico) throws AcademicoNaoExisteException, CampeonatoInvalidoException, RegistroNaoEncontradoException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);

        if (academico.isPresent()){
            if (campeonato.isPresent()){
                Optional<Jogador> jogador = jogadorRepository.findByAcademicoAndTimeCampeonato(academico.get(), campeonato.get());
                if (jogador.isPresent()){
                    float media = 0;
                    int contador = 0;

                    for(int i = 0; i < jogador.get().getAvaliacoes().size(); i++){
                        if (jogador.get().getAvaliacoes().get(i).getNota() != 0){
                            contador ++;
                            media += jogador.get().getAvaliacoes().get(i).getNota();
                        }
                    }
                    media = media / contador;
                    return media;
                }else {
                    throw new RegistroNaoEncontradoException("Nenhum registro do jogador encontrado!");
                }
            }else {
                throw new CampeonatoInvalidoException("Campeonato nao existente!");
            }
        }else {
            throw new AcademicoNaoExisteException("Academico nao encontrado!");
        }
    }

    public float recuperaAvaliacaoPorModalidade(Long idModalidade, Long idAcademico) throws AcademicoNaoExisteException, ModalidadeNaoExistenteException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);

        if (academico.isPresent()){
            if (modalidadeEsportiva.isPresent()){

                List<Jogador> jogador = jogadorRepository.findByAcademicoAndTimeCampeonatoModalidadeEsportiva(academico.get(), modalidadeEsportiva.get());

                float media = 0;
                int contador = 0;

                for(int x = 0; x < jogador.size(); x++){
                    for (int y = 0; y < jogador.get(x).getAvaliacoes().size();y++ ){
                        if (jogador.get(x).getAvaliacoes().get(y).getNota() != 0){
                            contador ++;
                            media += jogador.get(x).getAvaliacoes().get(y).getNota();
                        }
                    }
                }
                media = media / contador;
                return media;
            }else {
                throw new ModalidadeNaoExistenteException("Modalidade esportiva nao existente!");
            }
        }else {
            throw new AcademicoNaoExisteException("Academico nao encontrado!");
        }
    }

    public List<Campeonato> buscarHistoricoCampeonatoOutrosUsuarios(Long idAcademico) throws AcademicoNaoExisteException, ConteudoPrivadoException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()){
            Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);
            if (privacidade.isMostrarHistoricoCampeonatos()) {
                List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
                List<Campeonato> campeonatos = new ArrayList<Campeonato>();
                for (int i = 0; i < participacoesCampeonatos.size(); i++){
                    campeonatos.add(participacoesCampeonatos.get(i).getTime().getCampeonato());
                }
               return campeonatos;
            }else {
                throw new ConteudoPrivadoException("O usuario definiu seu historico como privadas!");
            }
        }else {
            throw new AcademicoNaoExisteException("Academico nao encontrado!");
        }
    }
}
