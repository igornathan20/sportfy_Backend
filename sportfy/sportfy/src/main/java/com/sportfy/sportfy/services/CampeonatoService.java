package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.enums.TipoFasePartida;
import com.sportfy.sportfy.enums.TipoPrivacidadeCampeonato;
import com.sportfy.sportfy.enums.TipoSituacao;
import com.sportfy.sportfy.enums.TipoSituacaoJogador;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private AvaliacaoJogadorRepository avaliacaoJogadorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CampeonatoResponseDto criarCampeonato(CampeonatoDto campeonatoDto) throws AcademicoNaoExisteException, ModalidadeNaoExistenteException, CampeonatoInvalidoException {
        Optional<Academico> academico = academicoRepository.findById(campeonatoDto.idAcademico());
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(campeonatoDto.idModalidadeEsportiva());
        Optional<List<Campeonato>> campeonatos = campeonatoRepository.findByTituloAndAtivo(campeonatoDto.titulo(), true);

        if (academico.isPresent()) {
            if (modalidade.isPresent()) {
                if (campeonatos.isPresent() && campeonatos.get().isEmpty()) {
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
                        if (campeonatoDto.senha() != null){
                            novoCampeonato.setSenha(passwordEncoder.encode(campeonatoDto.senha()));
                        }
                        System.out.println("teste: " + novoCampeonato.getDataFim() + novoCampeonato.getTitulo());
                        return novoCampeonato.toResponseDto(campeonatoRepository.save(novoCampeonato));
                    } catch (Exception e) {
                        return null;
                    }
                }else {
                    throw  new CampeonatoInvalidoException("Um campeonato com o titulo " + campeonatoDto.titulo() + " ja exite!");
                }
            } else {
                throw new ModalidadeNaoExistenteException("Modalidade esportiva nao cadastrada!");
            }
        } else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }

    public CampeonatoResponseDto editarCampeonato(Long idCampeonato, CampeonatoDto campeonatoDto) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);

        if (campeonato.isPresent()) {
            Campeonato editCampeonato = campeonato.get();
            try {
                editCampeonato.toEntity(campeonatoDto);
                Endereco enderecoCampeonato = editCampeonato.getEndereco();
                enderecoCampeonato.toEntity(campeonatoDto.endereco());
                editCampeonato.setEndereco(enderecoCampeonato);
                if (campeonatoDto.senha() != null){
                    editCampeonato.setSenha(passwordEncoder.encode(campeonatoDto.senha()));
                }
                return editCampeonato.toResponseDto(campeonatoRepository.save(editCampeonato));
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new RegistroNaoEncontradoException("Campeonato não encontrado!");
        }
    }

    public Page<CampeonatoResponseDto> listarTodosCampeonatos(Pageable pageable) throws RegistroNaoEncontradoException {
        Page<Campeonato> campeonatos = campeonatoRepository.findAll(pageable);

        if (campeonatos.isEmpty()) {
            throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
        } else {
            return campeonatos.map(campeonato -> campeonato.toResponseDto(campeonato));
        }
    }

    public List<CampeonatoResponseDto> listarCampeonatosComFiltro(CampeonatoDto campeonatoDto) throws RegistroNaoEncontradoException {
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

        if (campeonatoDto.privacidadeCampeonato() != "PUBLICO") {
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

        if (campeonatoDto.situacaoCampeonato() != "EM_ABERTO") {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("situacaoCampeonato"), campeonatoDto.situacaoCampeonato()));
        }

        List<CampeonatoResponseDto> campeonatos = campeonatoRepository.findAll(spec).stream().map(
                campeonato -> campeonato.toResponseDto(campeonato)
        ).collect(Collectors.toList());

        if (campeonatos.isEmpty()) {
            throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
        }
        return campeonatos;
    }

    public Page<CampeonatoResponseDto> listarCampeonatosPorModalidadesInscritas(Long idAcademico, Pageable pageable)
            throws RegistroNaoEncontradoException, AcademicoNaoExisteException {
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            List<CampeonatoResponseDto> campeonatosAcademico = new ArrayList<>();
            List<ModalidadeEsportiva> modalidadesEsportivas = academico.get().getModalidadeEsportivas();

            if (modalidadesEsportivas.isEmpty()) {
                throw new RegistroNaoEncontradoException("Usuário não cadastrado em nenhuma modalidade!");
            } else {
                for (ModalidadeEsportiva modalidade : modalidadesEsportivas) {
                    List<Campeonato> campeonatosEncontrados = campeonatoRepository.findByModalidadeEsportiva(modalidade);

                    // Adiciona cada campeonato encontrado à lista de campeonatos do acadêmico
                    campeonatosEncontrados.stream()
                            .map(campeonato -> campeonato.toResponseDto(campeonato))
                            .forEach(campeonatosAcademico::add);
                }
            }

            if (campeonatosAcademico.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
            }

            // Aplicar paginação manualmente na lista final de campeonatos
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), campeonatosAcademico.size());
            List<CampeonatoResponseDto> paginatedList = campeonatosAcademico.subList(start, end);

            return new PageImpl<>(paginatedList, pageable, campeonatosAcademico.size());
        } else {
            throw new AcademicoNaoExisteException("Acadêmico não encontrado!");
        }
    }


    public Page<CampeonatoResponseDto> listarCampeonatosInscritos(Long idAcademico, Pageable pageable) throws RegistroNaoEncontradoException, AcademicoNaoExisteException {
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
            List<CampeonatoResponseDto> campeonatos = new ArrayList<CampeonatoResponseDto>();

            for (int i = 0; i < participacoesCampeonatos.size(); i++){
                campeonatos.add(participacoesCampeonatos.get(i).getTime().getCampeonato().toResponseDto(participacoesCampeonatos.get(i).getTime().getCampeonato()));
            }

            if (campeonatos.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
            }
            int start = Math.min((int) pageable.getOffset(), campeonatos.size());
            int end = Math.min((start + pageable.getPageSize()), campeonatos.size());
            List<CampeonatoResponseDto> paginatedList = campeonatos.subList(start, end);

            return new PageImpl<>(paginatedList, pageable, campeonatos.size());
        } else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }

    public Page<CampeonatoResponseDto> listarCampeonatosCriados(Long idAcademico, Pageable pageable ) throws RegistroNaoEncontradoException, AcademicoNaoExisteException {
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            Page<Campeonato> campeonatosCriados = campeonatoRepository.findByAcademico(academico.get(), pageable);

            if (campeonatosCriados.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
            }
            return campeonatosCriados.map(campeonato -> campeonato.toResponseDto(campeonato));
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

    public CampeonatoResponseDto desativarCampeonato(Long id) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);

        if (campeonato.isPresent()) {
            Campeonato campeonatoDesativado = campeonato.get();
            campeonatoDesativado.setAtivo(false);
            return campeonato.get().toResponseDto(campeonatoRepository.save(campeonatoDesativado));
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

    public TimeDto criarTime(TimeDto novoTime) throws CampeonatoInvalidoException, TimeInvalidoException, PasswordInvalidoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(novoTime.campeonato());
        Optional<Time> timeEncontrado = timeRepository.findByNomeAndCampeonato(novoTime.nome(), campeonato.get());

        if (timeEncontrado.isEmpty()) {
            if (campeonato.get().getDataFim().isAfter(OffsetDateTime.now()) && campeonato.get().getSituacaoCampeonato() == TipoSituacao.EM_ABERTO && campeonato.get().getLimiteParticipantes() > 1) {
                if (campeonato.get().getPrivacidadeCampeonato() == TipoPrivacidadeCampeonato.PUBLICO || passwordEncoder.matches(novoTime.senhaCampeonato(), campeonato.get().getSenha())){
                    Time timeCriado = new Time();
                    timeCriado.setNome(novoTime.nome());
                    timeCriado.setCampeonato(campeonato.get());
                    return timeCriado.toDto(timeRepository.save(timeCriado));
                }else {
                    throw new PasswordInvalidoException("Senha do campeonato invalida!");
                }
            } else {
                throw new CampeonatoInvalidoException("O campeonato ja esta finalizado!");
            }
        } else{
            throw new TimeInvalidoException("Um time com esse nome ja esta cadastrado!");
        }
    }

    public List<TimeDto> listarTimesCampeonato(Long idCampeonato) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);

        if (campeonato.isPresent()) {
            List<TimeDto> times = timeRepository.findByCampeonato(campeonato.get()).stream()
                    .map( time -> time.toDto(time)).collect(Collectors.toList());
            if (times.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum time cadastrado no campeonato!");
            }
            return times;
        } else {
            throw new RegistroNaoEncontradoException("O campeonato com id " + idCampeonato + " não foi encontrado");
        }
    }

    public JogadorDto adicionarJogadorTime(TimeDto timeDto, Long idAcademico) throws CampeonatoInvalidoException, TimeInvalidoException, PasswordInvalidoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(timeDto.campeonato());
        Optional<Time> timeEncontrado = timeRepository.findByNomeAndCampeonato(timeDto.nome(), campeonato.get());
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (timeEncontrado.isPresent()) {
            List<Jogador> jogadores = jogadorRepository.findByTime(timeEncontrado.get());
            if (jogadores.size() <= campeonato.get().getLimiteParticipantes()){
                if (campeonato.get().getDataFim().isAfter(OffsetDateTime.now()) && campeonato.get().getSituacaoCampeonato() != TipoSituacao.FINALIZADO ) {
                    if (campeonato.get().getPrivacidadeCampeonato() == TipoPrivacidadeCampeonato.PUBLICO || passwordEncoder.matches(timeDto.senhaCampeonato(), campeonato.get().getSenha())){
                        Jogador novoJogador = new Jogador();
                        novoJogador.setModalidadeEsportiva(campeonato.get().getModalidadeEsportiva());
                        novoJogador.setAcademico(academico.get());
                        novoJogador.setTime(timeEncontrado.get());
                        return novoJogador.toDto(jogadorRepository.save(novoJogador));
                    }else {
                        throw new PasswordInvalidoException("Senha do campeonato invalida!");
                    }
                } else {
                    throw new CampeonatoInvalidoException("O campeonato ja esta finalizado!");
                }
            }else {
                throw new TimeInvalidoException("Limite de jogadores no time excedido!");
            }
        } else{
            throw new TimeInvalidoException("Time invalido!");
        }
    }

    public TimeDto criarTimeComUmJogador(Long idCampeonato, Long idAcademico, String senhaCampeonato) throws CampeonatoInvalidoException, TimeInvalidoException, RegistroNaoEncontradoException, PasswordInvalidoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        Optional<Academico> academico = academicoRepository.findById(idAcademico);
        if (campeonato.isPresent() && academico.isPresent()){
            Optional<Time> timeEncontrado = timeRepository.findByNomeAndCampeonato(academico.get().getUsuario().getUsername(), campeonato.get());
            if (timeEncontrado.isEmpty()) {
                if (campeonato.get().getDataFim().isAfter(OffsetDateTime.now()) && campeonato.get().getSituacaoCampeonato() != TipoSituacao.FINALIZADO ) {
                    if (campeonato.get().getLimiteParticipantes() == 1){
                        if (campeonato.get().getPrivacidadeCampeonato() == TipoPrivacidadeCampeonato.PUBLICO || passwordEncoder.matches(senhaCampeonato, campeonato.get().getSenha())){
                            Time timeCriado = new Time();
                            timeCriado.setNome(academico.get().getUsuario().getUsername());
                            timeCriado.setCampeonato(campeonato.get());

                            Jogador novoJogador = new Jogador();
                            novoJogador.setModalidadeEsportiva(campeonato.get().getModalidadeEsportiva());
                            novoJogador.setAcademico(academico.get());
                            novoJogador.setTime(timeCriado);
                            jogadorRepository.save(novoJogador);
                            return timeCriado.toDto(timeCriado);
                        }else {
                            throw new PasswordInvalidoException("Senha do campeonato invalida!");
                        }
                    }else {
                        throw new TimeInvalidoException("Limite de jogadores no time excedido!");
                    }
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

    public Page<JogadorDto> listarJogadoresCampeonato(Long idCampeonato, Pageable pageable) throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);

        if (campeonato.isPresent()) {
            List<JogadorDto> jogadores = jogadorRepository.findByTimeCampeonato(campeonato.get()).stream().map(
                    jogador -> jogador.toDto(jogador)
            ).collect(Collectors.toList());

            if (jogadores.isEmpty()) {
                throw new RegistroNaoEncontradoException("Nenhum jogador foi encontrado!");
            }

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), jogadores.size());
            List<JogadorDto> paginatedList = jogadores.subList(start, end);

            return new PageImpl<>(paginatedList, pageable, jogadores.size());
        } else {
            throw new RegistroNaoEncontradoException("O campeonato com id " + idCampeonato + " não foi encontrado");
        }
    }

    public Page<AcademicoDto> listarJogadoresEnfrentados(Long idAcademico, Pageable pageable)
            throws RegistroNaoEncontradoException, AcademicoNaoExisteException {
        Academico academico = academicoRepository.findById(idAcademico)
                .orElseThrow(() -> new AcademicoNaoExisteException("O academico com id " + idAcademico + " não foi encontrado"));

        // Encontrar todos os times em que o acadêmico jogou
        List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico);
        List<Time> meusTimes = participacoesCampeonatos.stream()
                .map(Jogador::getTime)
                .collect(Collectors.toList());

        // Encontrar todas as partidas dos meus times e coletar os times envolvidos
        Set<Time> timesPartidas = meusTimes.stream()
                .flatMap(time -> partidaRepository.findByTime1OrTime2(time, time).stream())
                .flatMap(partida -> Stream.of(partida.getTime1(), partida.getTime2()))
                .collect(Collectors.toSet());

        // Buscar jogadores enfrentados nos times encontrados e transformar em AcademicoDto
        List<AcademicoDto> academicosEnfrentados = timesPartidas.stream()
                .flatMap(time -> jogadorRepository.findByTime(time).stream())
                .map(Jogador::getAcademico)
                .filter(jogador -> !jogador.equals(academico)) // Excluir o próprio acadêmico
                .map(Academico::toDto)
                .distinct()
                .collect(Collectors.toList());
        if (academicosEnfrentados.isEmpty()){
            throw new RegistroNaoEncontradoException("Nenhum jogador enfrentado!");
        }

        // Aplicar paginação manualmente
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), academicosEnfrentados.size());
        List<AcademicoDto> paginatedList = academicosEnfrentados.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, academicosEnfrentados.size());
    }

    public JogadorDto mudarSituacaoJogador(Long idJogador, String situacao ) throws TipoInvalidoException, RegistroNaoEncontradoException{
        Optional<Jogador> jogador = jogadorRepository.findById(idJogador);

        if (jogador.isPresent()){
            switch (situacao){
                case "EM_ABERTO":
                    jogador.get().setSituacaoJogador(TipoSituacaoJogador.EM_ABERTO);
                    break;
                case "ATIVO":
                    jogador.get().setSituacaoJogador(TipoSituacaoJogador.ATIVO);
                    break;
                case "BLOQUEADO":
                    jogador.get().setSituacaoJogador(TipoSituacaoJogador.BLOQUEADO);
                    break;
                case "FINALIZADO":
                    jogador.get().setSituacaoJogador(TipoSituacaoJogador.FINALIZADO);
                    break;
                default:
                    throw new TipoInvalidoException("O tipo situação do jogador é invalido!");
            }
            return jogador.get().toDto(jogadorRepository.save(jogador.get()));
        }else {
            throw new RegistroNaoEncontradoException("Nenhum registro do jogador foi encontrado!");
        }
    }

    public List<PartidaDto> definirPrimeiraFase(Long idCampeonato) throws RegistroNaoEncontradoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        if (campeonato.isPresent()){
            List<Time> times = timeRepository.findByCampeonato(campeonato.get());

            for (int i = 0; i < times.size();i ++){
                List<Jogador> jogador = jogadorRepository.findByTime(times.get(i));
                if (campeonato.get().getLimiteParticipantes() == 1) {
                    if (jogador.getFirst().getSituacaoJogador() == TipoSituacaoJogador.BLOQUEADO) {
                        jogadorRepository.delete(jogador.getFirst());
                        timeRepository.delete(times.get(i));
                        times.remove(times.get(i));
                    }
                }
                if (campeonato.get().getLimiteParticipantes() > 1){
                    for (Jogador j : jogador) {
                        if (j.getSituacaoJogador() == TipoSituacaoJogador.BLOQUEADO) {
                            jogadorRepository.delete(j);
                        }
                    }
                    if (jogador.isEmpty()){
                        timeRepository.delete(times.get(i));
                        times.remove(times.get(i));
                    }
                }
            }


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

            List<Jogador> jogadores = jogadorRepository.findByTimeCampeonato(campeonato.get());
            for (int i = 0; i < jogadores.size(); i++){
                jogadores.get(i).setSituacaoJogador(TipoSituacaoJogador.ATIVO);
                jogadorRepository.save(jogadores.get(i));
            }

            //campeonato.setPartidas(partidas);
            partidaRepository.saveAll(partidas);
            campeonato.get().setSituacaoCampeonato(TipoSituacao.INICIADO);
            campeonatoRepository.save(campeonato.get());
            return partidas.stream().map(p -> p.toDto(p)).collect(Collectors.toList());
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

    public List<PartidaDto> listarPartidas(Long idCampeonato)throws RegistroNaoEncontradoException {
        Optional<Campeonato> campeonato = campeonatoRepository.findById(idCampeonato);
        if (campeonato.isPresent()) {
            List<PartidaDto> partidas = partidaRepository.findByCampeonato(campeonato.get()).stream().map(p -> p.toDto(p)).collect(Collectors.toList());
            if(partidas.isEmpty()){
                throw new RegistroNaoEncontradoException("Nenhuma partida foi encontrada no campeonato!");
            }else {
                return partidas;
            }
        }else {
            throw new RegistroNaoEncontradoException("Campeonato nao encontrado!");
        }
    }


    public List<PartidaDto> avancarDeFase(Long idCampeonato) throws RegistroNaoEncontradoException, AvancarFaseException{
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
                if (partidasFaseAnterior.get(i).getResultado().getPontuacaoTime1() == partidasFaseAnterior.get(i).getResultado().getPontuacaoTime2()) {
                    throw new AvancarFaseException("Não é possivel avancar de fase pois existem partidas sem vencedor definido!");
                }
                times.add(partidasFaseAnterior.get(i).getResultado().getVencedor());
                partidasFaseAnterior.get(i).setSituacaoPartida(TipoSituacao.FINALIZADO);
            }

            for (int i = 0; i < times.size();i ++){
                List<Jogador> jogador = jogadorRepository.findByTime(times.get(i));
                if (campeonato.get().getLimiteParticipantes() == 1) {
                    if (jogador.getFirst().getSituacaoJogador() == TipoSituacaoJogador.BLOQUEADO) {
                        jogadorRepository.delete(jogador.getFirst());
                        timeRepository.delete(times.get(i));
                        times.remove(times.get(i));
                    }
                }
                if (campeonato.get().getLimiteParticipantes() > 1){
                    for (Jogador j : jogador) {
                        if (j.getSituacaoJogador() == TipoSituacaoJogador.BLOQUEADO) {
                            jogadorRepository.delete(j);
                        }
                    }
                    if (jogador.isEmpty()){
                        timeRepository.delete(times.get(i));
                        times.remove(times.get(i));
                    }
                }
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
                case TipoFasePartida.FINAL:
                    finalizarCampeonato(campeonato.get());
                    return new ArrayList<>();
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
            return partidaRepository.saveAll(partidas).stream().map(p -> p.toDto(p)).collect(Collectors.toList());
        }else {
            throw new RegistroNaoEncontradoException("Campeonato nao encontrado!");
        }
    }

    public void finalizarCampeonato(Campeonato campeonato)  {
        List<Partida> partidas = partidaRepository.findByCampeonato(campeonato);
        List<Jogador> jogadores = jogadorRepository.findByTimeCampeonato(campeonato);

        campeonato.setSituacaoCampeonato(TipoSituacao.FINALIZADO);
        campeonato.setAtivo(false);
        campeonato.setDataFim(OffsetDateTime.now());

        for (int i = 0; i < partidas.size(); i ++){
            partidas.get(i).setSituacaoPartida(TipoSituacao.FINALIZADO);

            if (partidas.get(i).getFasePartida() == TipoFasePartida.FINAL){
                if (partidas.get(i).getTime1() == null) {
                    partidas.get(i).getResultado().setVencedor(partidas.get(i).getTime2());
                }else if (partidas.get(i).getTime2() == null) {
                    partidas.get(i).getResultado().setVencedor(partidas.get(i).getTime1());
                }

                if (partidas.get(i).getResultado().getPontuacaoTime1() > partidas.get(i).getResultado().getPontuacaoTime2()){
                    partidas.get(i).getResultado().setVencedor(partidas.get(i).getTime1());
                }else {
                    partidas.get(i).getResultado().setVencedor(partidas.get(i).getTime2());
                }
            }
        }

        for (int i = 0; i < jogadores.size(); i ++) {
            if (jogadores.get(i).getSituacaoJogador() != TipoSituacaoJogador.BLOQUEADO){
                jogadores.get(i).setSituacaoJogador(TipoSituacaoJogador.FINALIZADO);
            }
        }
        partidaRepository.saveAll(partidas);
        jogadorRepository.saveAll(jogadores);
        campeonatoRepository.save(campeonato);
    }


    public PartidaDto alterarPontuacaoPartida(Long idPartida, int pontuacaoTime1, int pontuacaoTime2) throws RegistroNaoEncontradoException{
        Optional<Partida> partida = partidaRepository.findById(idPartida);

        if (partida.isPresent()){
            partida.get().setSituacaoPartida(TipoSituacao.EM_ABERTO);
            partida.get().getResultado().setPontuacaoTime1(pontuacaoTime1);
            partida.get().getResultado().setPontuacaoTime2(pontuacaoTime2);

            if (partida.get().getResultado().getPontuacaoTime1() > partida.get().getResultado().getPontuacaoTime2()){
                partida.get().getResultado().setVencedor(partida.get().getTime1());
            }else if (partida.get().getResultado().getPontuacaoTime1() < partida.get().getResultado().getPontuacaoTime2()){
                partida.get().getResultado().setVencedor(partida.get().getTime2());
            }
            return  partida.get().toDto(partidaRepository.save(partida.get()));
        }
        throw new RegistroNaoEncontradoException("nao foi encontrado registro da partida!");
    }

    public AvaliacaoJogadorDto avaliarJogador (Long idAvaliador, Long idAcademicoAvaliado, Long idModalidade, int nota )throws AcademicoNaoExisteException, RegistroNaoEncontradoException{
        Optional<Academico> avaliador = academicoRepository.findById(idAvaliador);
        Optional<Academico> academicoAvaliado = academicoRepository.findById(idAcademicoAvaliado);
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);

        if(avaliador.isPresent() && academicoAvaliado.isPresent()){
            if (modalidadeEsportiva.isPresent()){
                AvaliacaoJogador avaliacao = new AvaliacaoJogador();
                avaliacao.setAcademicoAvaliado(academicoAvaliado.get());
                avaliacao.setAvaliador(avaliador.get());
                avaliacao.setModalidadeEsportiva(modalidadeEsportiva.get());
                avaliacao.setNota(nota);
                return avaliacao.toDto(avaliacaoJogadorRepository.save(avaliacao));
            }else {
                throw new RegistroNaoEncontradoException("Jogador não existente!");
            }
        }else {
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }


    public AvaliacaoResponseDto recuperaAvaliacaoPorModalidade(Long idModalidade, Long idAcademico) throws AcademicoNaoExisteException, ModalidadeNaoExistenteException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);

        if (academico.isPresent()){
            if (modalidadeEsportiva.isPresent()){
                List<AvaliacaoJogador> avaliacoes = avaliacaoJogadorRepository.findByAcademicoAvaliadoAndModalidadeEsportiva(academico.get(),modalidadeEsportiva.get());
                float media = 0;
                int contador = 0;

                for (int y = 0; y < avaliacoes.size(); y++ ){
                    if (avaliacoes.get(y).getNota() != 0){
                        contador ++;
                        media += avaliacoes.get(y).getNota();
                    }
                }

                media = media / contador;
                return new AvaliacaoResponseDto(media, contador);
            }else {
                throw new ModalidadeNaoExistenteException("Modalidade esportiva nao existente!");
            }
        }else {
            throw new AcademicoNaoExisteException("Academico nao encontrado!");
        }
    }

    public List<CampeonatoResponseDto> buscarHistoricoCampeonatoOutrosUsuarios(Long idAcademico) throws AcademicoNaoExisteException, ConteudoPrivadoException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()){
            Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);
            if (privacidade.isMostrarHistoricoCampeonatos()) {
                List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
                List<CampeonatoResponseDto> campeonatos = new ArrayList<CampeonatoResponseDto>();
                for (int i = 0; i < participacoesCampeonatos.size(); i++){
                    campeonatos.add(participacoesCampeonatos.get(i).getTime().getCampeonato().toResponseDto(participacoesCampeonatos.get(i).getTime().getCampeonato()));
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
