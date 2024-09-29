package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.*;
import com.sportfy.sportfy.util.EnviarEmail;
import com.sportfy.sportfy.util.GeraSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private PermissaoRepository permissaoRepository;
    @Autowired
    private PrivacidadeRepository privacidadeRepository;
    @Autowired
    private NotificacaoRepository notificacaoRepository;


    public AcademicoDto cadastrar(AcademicoDto academicoDto) throws EmailInvalidoException, OutroUsuarioComDadosJaExistentes, PermissaoNaoExisteException {
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
    
        Optional<Permissao> permissao = permissaoRepository.findByTipoPermissao(TipoPermissao.ACADEMICO);
        if (!permissao.isPresent()) {
            throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", TipoPermissao.ACADEMICO));
        }
    
        if (existAcademicoBD.get().size() == 0) {
            Academico novoAcademico = new Academico();
            String senha = "1234"; //GeraSenha.generatePassword();
            novoAcademico.toEntity(academicoDto);
            novoAcademico.getUsuario().setPassword(passwordEncoder.encode(senha));
            novoAcademico.getUsuario().setPermissao(permissao.get());
            Academico academicoCriado = academicoRepository.save(novoAcademico);

            Privacidade privacidadeUser = new Privacidade();
            privacidadeUser.setIdAcademico(academicoCriado.getIdAcademico());
            privacidadeRepository.save(privacidadeUser);

            Notificacao notificacaoUser = new Notificacao();
            notificacaoUser.setIdAcademico(academicoCriado.getIdAcademico());
            notificacaoRepository.save(notificacaoUser);

            try {
                String mensagem = "Bem vindo ao Sportfy! Acesse a sua conta com o username cadastrado e a sua senha: \n\n" + senha +
                "\n\n\nBora se movimentar e fazer amigos?\n O Sportfy irá te ajudar!";
                email.sendEmail(academicoCriado.getEmail(), "Sportfy seu app de eventos esportivos!", mensagem);
            } catch (Exception e) {
                System.err.println("Erro ao enviar email: " + e.getMessage());
            }
            return AcademicoDto.fromAcademicoBD(academicoCriado);  
        } else if (existAcademicoBD.get().size() == 1) {
            Academico academicoAtualizado = new Academico();
            String senha = GeraSenha.generatePassword();
            academicoAtualizado.atualizar(existAcademicoBD.get().get(0).getIdAcademico(), existAcademicoBD.get().get(0).getUsuario().getIdUsuario(), academicoDto);
            academicoAtualizado.getUsuario().setPassword(passwordEncoder.encode(senha));
            academicoAtualizado.getUsuario().setPermissao(existAcademicoBD.get().get(0).getUsuario().getPermissao());
            Academico academicoSalvo = academicoRepository.save(academicoAtualizado);
            return AcademicoDto.fromAcademicoBD(academicoSalvo);
        } else {
            throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username eou email já existente!");
        }
    }
    
    public AcademicoDto atualizar(Long idAcademico, AcademicoDto academicoDto) throws EmailInvalidoException, AcademicoNaoExisteException, OutroUsuarioComDadosJaExistentes {
        if (!isEmailFromUfpr(academicoDto.email())) {
            throw new EmailInvalidoException("Email invalido!");
        }
        Optional<Academico> academicoBD = academicoRepository.findByIdAcademicoAndUsuarioAtivo(idAcademico, true);
        if (!academicoBD.isPresent()) {
            throw new AcademicoNaoExisteException("Academico não existe!");
        }
    
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
        return AcademicoDto.fromAcademicoBD(academicoSalvo);
    }
    
    public AcademicoDto inativar(Long idAcademico) throws AcademicoNaoExisteException {
        return academicoRepository.findByIdAcademicoAndUsuarioAtivo(idAcademico, true).map(academicoBD -> {
            academicoBD.getUsuario().inativar();
            Academico academicoInativado = academicoRepository.save(academicoBD);
            return AcademicoDto.fromAcademicoBD(academicoInativado);
        }).orElseThrow(() -> new AcademicoNaoExisteException("Academico não existe!"));
    }

    public AcademicoDto consultar(Long idUsuario) throws AcademicoNaoExisteException {
        return academicoRepository.findByUsuarioIdUsuarioAndUsuarioAtivo(idUsuario, true).map(academicoBD -> {
            return AcademicoDto.fromAcademicoBD(academicoBD);
        }).orElseThrow(() -> new AcademicoNaoExisteException("Academico não existe!"));
    }

    public List<AcademicoDto> listar() throws ListaAcademicosVaziaException {
        Optional<List<Academico>> listaAcademicoBD = academicoRepository.findByUsuarioAtivo(true);
        if (listaAcademicoBD.isPresent()) {
            List<AcademicoDto> listaAcademicoDto = listaAcademicoBD.get().stream().map(academicoBD -> AcademicoDto.fromAcademicoBD(academicoBD)).collect(Collectors.toList());
            return listaAcademicoDto;
        } else {
            throw new ListaAcademicosVaziaException("Lista de acadêmicos vazia!");
        }
    }

    public boolean isEmailFromUfpr(String email) {
        return email != null && email.endsWith("@ufpr.br");
    }

    public boolean retornaNotificacao(Long idAcademico, String tipo){
        Notificacao notificacao = notificacaoRepository.findByIdAcademico(idAcademico);

        switch (tipo){
            case "notificarCampeonatos":
                return notificacao.isNotificarCampeonatos();
            case "notificarPosts":
                return notificacao.isNotificarPosts();
            case "notificarComentarios":
                return notificacao.isNotificarComentarios();
            case "notificarLikes":
                return notificacao.isNotificarLikes();
            default:
                return true;
        }
    }

    public Notificacao retornaTodasNotificacoes(Long idAcademico){
       return notificacaoRepository.findByIdAcademico(idAcademico);
    }

    public Notificacao alteraNotificacao(NotificacaoDto userNotificacao){
        Notificacao notificacao = notificacaoRepository.findByIdAcademico(userNotificacao.idAcademico());

        notificacao.setNotificarCampeonatos(userNotificacao.campeonatos());
        notificacao.setNotificarPosts(userNotificacao.posts());
        notificacao.setNotificarComentarios(userNotificacao.comentarios());
        notificacao.setNotificarLikes(userNotificacao.likes());

        return notificacaoRepository.save(notificacao);
    }

    public boolean retornaPrivacidade(Long idAcademico, String tipo){
        Privacidade privacidade = privacidadeRepository.findByIdAcademico(idAcademico);

        switch (tipo){
            case "mostrarModalidadesEsportivas":
                return privacidade.isMostrarModalidadesEsportivas();
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

    public Privacidade retornaTodasPrivacidade(Long idAcademico){
        return privacidadeRepository.findByIdAcademico(idAcademico);
    }

    public Privacidade alteraPrivacidade(PrivacidadeDto userPrivacidade){
        Privacidade privacidade = privacidadeRepository.findByIdAcademico(userPrivacidade.idAcademico());

        privacidade.setMostrarConquistas(userPrivacidade.mostrarConquistas());
        privacidade.setMostrarHistoricoCampeonatos(userPrivacidade.mostrarHistoricoCampeonatos());
        privacidade.setMostrarModalidadesEsportivas(userPrivacidade.mostrarModalidadesEsportivas());
        privacidade.setMostrarEstatisticasModalidadesEsportivas(userPrivacidade.mostrarEstatisticasModalidadesEsportivas());

        return privacidadeRepository.save(privacidade);
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
                return new EstatisticasPessoaisModalidadeDto(modalidadeEsportiva.get().getNome(), vitorias, derrotas, totalPartidas );
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
                    return new EstatisticasPessoaisModalidadeDto(modalidadeEsportiva.get().getNome(), vitorias, derrotas, totalPartidas);
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

    public EstatisticasDeUsoDto estatisticasDeUso(Long idAcademico)throws AcademicoNaoExisteException, ModalidadeNaoExistenteException, RegistroNaoEncontradoException{
        Optional<Academico> academico = academicoRepository.findById(idAcademico);

        if (academico.isPresent()){
            List<AcademicoModalidadeEsportiva> academicoModalidade = academicoModalidadeEsportivaReposity.findByAcademico(academico.get());
            List<Jogador> participacoesCampeonatos = jogadorRepository.findByAcademico(academico.get());
            //fazer para buscar posts posteriormente
            return new EstatisticasDeUsoDto(participacoesCampeonatos.size(), academicoModalidade.size());
        }else {
            throw new ModalidadeNaoExistenteException("Modalidade nao encontrada!");
        }
    }


}
