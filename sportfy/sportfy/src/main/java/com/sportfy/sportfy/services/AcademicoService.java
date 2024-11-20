package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.enums.TipoSituacaoMetaDiaria;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;
import com.sportfy.sportfy.util.EnviarEmail;
import com.sportfy.sportfy.util.GeraSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AcademicoService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EnviarEmail email;
    @Autowired
    AcademicoRepository academicoRepository;
    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;
    @Autowired
    PartidaRepository partidaRepository;
    @Autowired
    JogadorRepository jogadorRepository;
    @Autowired
    AcademicoModalidadeEsportivaRepository academicoModalidadeEsportivaReposity;
    @Autowired
    private PrivacidadeRepository privacidadeRepository;
    @Autowired
    MetaDiariaRepository metaDiariaRepository;
    @Autowired
    PublicacaoRepository publicacaoRepository;
    @Autowired
    CampeonatoService campeonatoService;


    public AcademicoResponseDto cadastrar(AcademicoDto academicoDto) throws EmailInvalidoException, OutroUsuarioComDadosJaExistentes {
        if (!isEmailFromUfpr(academicoDto.email())) {
            throw new EmailInvalidoException("Email invalido!");
        }

        Optional<List<Academico>> existAcademicoBD = academicoRepository.findByUsuarioUsernameOrEmail(academicoDto.username(), academicoDto.email());
        if (existAcademicoBD.isPresent() && !existAcademicoBD.get().isEmpty()) {
            boolean usernameExists = false;
            boolean emailExists = false;
            for (Academico academico : existAcademicoBD.get()) {
                if (academico.getUsuario().isAtivo()) {
                    if (academico.getUsuario().getUsername().equals(academicoDto.username())) {
                        usernameExists = true;
                    }
                    if (academico.getEmail().equals(academicoDto.email())) {
                        emailExists = true;
                    } 
                }
            }
            if (usernameExists && emailExists) {
                throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username e email já existente!");
            } else if (usernameExists) {
                throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username já existente!");
            } else if (emailExists) {
                throw new OutroUsuarioComDadosJaExistentes("Outro usuário com email já existente!");
            }
        }

        if (existAcademicoBD.get().isEmpty()) {
            Academico novoAcademico = new Academico();
            String senha = "1234";
            novoAcademico.toEntity(academicoDto);
            novoAcademico.getUsuario().setPassword(passwordEncoder.encode(senha));


            novoAcademico.getUsuario().setPermissao(TipoPermissao.ACADEMICO);
            Academico academicoCriado = academicoRepository.save(novoAcademico);

            Privacidade privacidadeUser = new Privacidade();
            privacidadeUser.setIdAcademico(academicoCriado.getIdAcademico());
            privacidadeRepository.save(privacidadeUser);

            try {
                String mensagem = "Bem vindo ao Sportfy! Acesse a sua conta com o username cadastrado e a sua senha: \n\n" + senha +
                "\n\n\nBora se movimentar e fazer amigos?\n O Sportfy irá te ajudar!";
                email.sendEmail(academicoCriado.getEmail(), "Sportfy seu app de eventos esportivos!", mensagem);
            } catch (Exception e) {
                System.err.println("Erro ao enviar email: " + e.getMessage());
            }
            return AcademicoResponseDto.fromAcademicoBD(academicoCriado);
        } else if (existAcademicoBD.get().size() == 1) {
            Academico academicoAtualizado = new Academico();
            String senha = GeraSenha.generatePassword();
            academicoAtualizado.atualizar(existAcademicoBD.get().get(0).getIdAcademico(), existAcademicoBD.get().get(0).getUsuario().getIdUsuario(), academicoDto);
            academicoAtualizado.getUsuario().setPassword(passwordEncoder.encode(senha));
            academicoAtualizado.getUsuario().setPermissao(existAcademicoBD.get().get(0).getUsuario().getPermissao());
            Academico academicoSalvo = academicoRepository.save(academicoAtualizado);
            return AcademicoResponseDto.fromAcademicoBD(academicoSalvo);
        } else {
            throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username eou email já existente!");
        }
    }
    
    public AcademicoResponseDto atualizar(Long idAcademico, AcademicoDto academicoDto, String usuarioAutenticado) throws EmailInvalidoException,AccessDeniedException, AcademicoNaoExisteException, OutroUsuarioComDadosJaExistentes {
        if (!isEmailFromUfpr(academicoDto.email())) {
            throw new EmailInvalidoException("Email invalido!");
        }
        Optional<Academico> academicoBD = academicoRepository.findByIdAcademicoAndUsuarioAtivo(idAcademico, true);
        if (!academicoBD.isPresent()) {
            throw new AcademicoNaoExisteException("Academico não existe!");
        }
        if (academicoBD.get().getUsuario().getUsername().equals(usuarioAutenticado)){
            Optional<List<Academico>> academicoExistente = academicoRepository.findByUsuarioUsernameOrEmail(academicoDto.username(), academicoDto.email());
            if (academicoExistente.isPresent()) {
                List<Academico> academicos = academicoExistente.get();
                boolean usernameOrEmailExists = academicos.stream()
                        .anyMatch(academico -> !academico.getIdAcademico().equals(academicoBD.get().getIdAcademico()));

                if (usernameOrEmailExists) {
                    boolean usernameExists = academicos.stream()
                            .anyMatch(academico -> !academico.getIdAcademico().equals(academicoBD.get().getIdAcademico())
                                    && academico.getUsuario().getUsername().equals(academicoDto.username()));

                    if (usernameExists) {
                        throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username já existente!");
                    } else {
                        throw new OutroUsuarioComDadosJaExistentes("Outro usuário com email já existente!");
                    }
                }
            }

            Academico academicoAtualizado = new Academico();
            academicoAtualizado.atualizar(academicoBD.get().getIdAcademico(), academicoBD.get().getUsuario().getIdUsuario(), academicoDto);
            academicoAtualizado.getUsuario().setDataCriacao(academicoBD.get().getUsuario().getDataCriacao());
            academicoAtualizado.getUsuario().setPermissao(academicoBD.get().getUsuario().getPermissao());
            if (academicoDto.password() == null || academicoDto.password().isEmpty()) {
                academicoAtualizado.getUsuario().setPassword(academicoBD.get().getUsuario().getPassword());
            } else {
                academicoAtualizado.getUsuario().setPassword(passwordEncoder.encode(academicoDto.password()));
            }

            Academico academicoSalvo = academicoRepository.save(academicoAtualizado);
            return AcademicoResponseDto.fromAcademicoBD(academicoSalvo);
        }else {
            throw new AccessDeniedException("Voce não tem permissão para alterar esse recurso!");
        }
    }
    
    public AcademicoResponseDto inativar(Long idAcademico) throws AcademicoNaoExisteException {
        return academicoRepository.findByIdAcademicoAndUsuarioAtivo(idAcademico, true).map(academicoBD -> {
            academicoBD.getUsuario().inativar();
            Academico academicoInativado = academicoRepository.save(academicoBD);
            return AcademicoResponseDto.fromAcademicoBD(academicoInativado);
        }).orElseThrow(() -> new AcademicoNaoExisteException("Academico não existe!"));
    }

    public AcademicoResponseDto consultar(Long idUsuario) throws AcademicoNaoExisteException {
        return academicoRepository.findByIdAcademicoAndUsuarioAtivo(idUsuario, true).map(AcademicoResponseDto::fromAcademicoBD).orElseThrow(() -> new AcademicoNaoExisteException("Academico não existe!"));
    }

    public AcademicoResponseDto buscarPorUsername(String userName) throws AcademicoNaoExisteException {
        return academicoRepository.findByUsuarioUsername(userName).map(AcademicoResponseDto::fromAcademicoBD).orElseThrow(() -> new AcademicoNaoExisteException("Academico não existe!"));
    }

    public Page<AcademicoResponseDto> listar(Pageable pageable) throws ListaAcademicosVaziaException {
        Page<Academico> listaAcademicoBD = academicoRepository.findByUsuarioAtivo(true, pageable);
        if (!listaAcademicoBD.isEmpty()) {
            Page<AcademicoResponseDto> listaAcademicoDto = listaAcademicoBD.map(AcademicoResponseDto::fromAcademicoBD);
            return listaAcademicoDto;
        } else {
            throw new ListaAcademicosVaziaException("Lista de acadêmicos vazia!");
        }
    }

    public boolean isEmailFromUfpr(String email) {
        return email != null && email.endsWith("@ufpr.br");
    }

    public boolean retornaPrivacidade(Long idAcademico, String tipo){
        Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);

        switch (tipo){
            case "mostrarHistoricoCampeonatos":
                return privacidade.isMostrarHistoricoCampeonatos();
            case "mostrarEstatisticasModalidadesEsportivas":
                return privacidade.isMostrarEstatisticasModalidadesEsportivas();
            case "mostrarConquistas":
                return privacidade.isMostrarConquistas();
            default:
                return true;
        }
    }

    public PrivacidadeDto retornaTodasPrivacidade(Long idAcademico){
        Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);
        return privacidade.toDto(privacidade);
    }

    public Privacidade alteraPrivacidade(PrivacidadeDto userPrivacidade, String academicoAutenticado) throws AccessDeniedException{
        Optional<Academico> academico = academicoRepository.findById(userPrivacidade.idAcademico());

        if (academico.get().getUsuario().getUsername().equals(academicoAutenticado)){
            Privacidade privacidade = privacidadeRepository.findByIdAcademico(userPrivacidade.idAcademico());

            privacidade.setMostrarConquistas(userPrivacidade.mostrarConquistas());
            privacidade.setMostrarHistoricoCampeonatos(userPrivacidade.mostrarHistoricoCampeonatos());
            privacidade.setMostrarEstatisticasModalidadesEsportivas(userPrivacidade.mostrarEstatisticasModalidadesEsportivas());

            return privacidadeRepository.save(privacidade);
        }else {
            throw new AccessDeniedException("Você não tem permissão para editar este recurso.");
        }
    }

    public EstatisticasPessoaisModalidadeDto estatisticasPessoaisPorModalidade(Long idAcademico , Long idModalidade)throws AcademicoNaoExisteException, ModalidadeNaoExistenteException, RegistroNaoEncontradoException{
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()){
            if (modalidadeEsportiva.isPresent()){
                List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
                int vitorias = 0;
                int derrotas = 0;
                int totalPartidas = 0;
                AvaliacaoResponseDto avaliacao = campeonatoService.recuperaAvaliacaoPorModalidade(idModalidade,idAcademico);


                if (participacoesCampeonatos == null){
                    throw new RegistroNaoEncontradoException("O jogador nao participou de nenhum campeonato!");
                }else {
                    List<Time> timesJogador = new ArrayList<>();
                    for (int i = 0; i < participacoesCampeonatos.size(); i++){
                        timesJogador.add(participacoesCampeonatos.get(i).getTime());
                    }

                    for (int x = 0; x < timesJogador.size(); x++){
                        List<Partida> partidas = partidaRepository.findByTime1OrTime2(timesJogador.get(x), timesJogador.get(x));
                        totalPartidas += partidas.size();

                        for (int y = 0; y < partidas.size(); y++){
                            if (partidas.get(y).getTime1() == timesJogador.get(x)){
                                if (partidas.get(y).getResultado().getPontuacaoTime1() > partidas.get(y).getResultado().getPontuacaoTime2()){
                                    vitorias ++;
                                }else {
                                    derrotas ++;
                                }
                            }
                            if (partidas.get(y).getTime2() == timesJogador.get(x)){
                                if (partidas.get(y).getResultado().getPontuacaoTime1() < partidas.get(y).getResultado().getPontuacaoTime2()){
                                    vitorias ++;
                                }else {
                                    derrotas ++;
                                }
                            }
                        }
                    }
                }
                return new EstatisticasPessoaisModalidadeDto(modalidadeEsportiva.get().getNome(), vitorias, derrotas, totalPartidas, avaliacao );
            }else {
                throw new AcademicoNaoExisteException("Academico nao encontrado!");
            }
        }else {
            throw new ModalidadeNaoExistenteException("Modalidade nao encontrada!");
        }
    }

    public EstatisticasPessoaisModalidadeDto estatisticasOutroUsuarioPorModalidade(Long idAcademico , Long idModalidade)throws AcademicoNaoExisteException, ModalidadeNaoExistenteException, RegistroNaoEncontradoException, ConteudoPrivadoException {
        Optional<ModalidadeEsportiva> modalidadeEsportiva = modalidadeEsportivaRepository.findById(idModalidade);
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()) {
            if (modalidadeEsportiva.isPresent()) {
                Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);
                if (privacidade.isMostrarEstatisticasModalidadesEsportivas()) {
                    List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
                    int vitorias = 0;
                    int derrotas = 0;
                    int totalPartidas = 0;
                    AvaliacaoResponseDto avaliacao = campeonatoService.recuperaAvaliacaoPorModalidade(idModalidade,idAcademico);


                    if (participacoesCampeonatos == null) {
                        throw new RegistroNaoEncontradoException("O jogador nao participou de nenhum campeonato!");
                    } else {
                        List<Time> timesJogador = new ArrayList<>();
                        for (int i = 0; i < participacoesCampeonatos.size(); i++) {
                            timesJogador.add(participacoesCampeonatos.get(i).getTime());
                        }

                        for (int x = 0; x < timesJogador.size(); x++) {
                            List<Partida> partidas = partidaRepository.findByTime1OrTime2(timesJogador.get(x), timesJogador.get(x));
                            totalPartidas += partidas.size();

                            for (int y = 0; y < partidas.size(); y++) {
                                if (partidas.get(y).getTime1() == timesJogador.get(x)) {
                                    if (partidas.get(y).getResultado().getPontuacaoTime1() > partidas.get(y).getResultado().getPontuacaoTime2()) {
                                        vitorias++;
                                    } else {
                                        derrotas++;
                                    }
                                }
                                if (partidas.get(y).getTime2() == timesJogador.get(x)) {
                                    if (partidas.get(y).getResultado().getPontuacaoTime1() < partidas.get(y).getResultado().getPontuacaoTime2()) {
                                        vitorias++;
                                    } else {
                                        derrotas++;
                                    }
                                }
                            }
                        }
                    }
                    return new EstatisticasPessoaisModalidadeDto(modalidadeEsportiva.get().getNome(), vitorias, derrotas, totalPartidas, avaliacao);
                }else {
                    throw new ConteudoPrivadoException("O usuario definiu suas estatisticas como privadas!");
                }
            } else {
                throw new AcademicoNaoExisteException("Academico nao encontrado!");
            }
        } else {
            throw new ModalidadeNaoExistenteException("Modalidade nao encontrada!");
        }
    }

    public EstatisticasPessoaisDto estatisticasDeUso(Long idAcademico)throws AcademicoNaoExisteException, ModalidadeNaoExistenteException, RegistroNaoEncontradoException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()){
            OffsetDateTime dataInscricao = academico.get().getUsuario().getDataCriacao();
            List<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaReposity.findByAcademico(academico.get());
            List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());

            List<MetaDiaria> metasDiarias = metaDiariaRepository.findByAcademico(academico.get());
            int metasDiariasRealizadas = (int) metasDiarias.stream()
                    .filter(metaDiaria -> TipoSituacaoMetaDiaria.CONCLUIDA.equals(metaDiaria.getTipoSituacaoMetaDiaria()))
                    .count();
            int metasDiariasAberto = (int) metasDiarias.stream()
                    .filter(metaDiaria -> TipoSituacaoMetaDiaria.EM_ANDAMENTO.equals(metaDiaria.getTipoSituacaoMetaDiaria()))
                    .count();

            List<Publicacao> posts = publicacaoRepository.findByUsuario(academico.get().getUsuario());

            return new EstatisticasPessoaisDto(dataInscricao, participacoesCampeonatos.size(),
                                                academicoModalidade.size(), metasDiariasRealizadas,
                                                metasDiariasAberto,
                                                posts.size()
                    );
        }else {
            throw new AcademicoNaoExisteException("Academico nao encontrado!");
        }
    }


}
