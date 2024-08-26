package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.EmailInvalidoException;
import com.sportfy.sportfy.exeptions.ListaAcademicosVaziaException;
import com.sportfy.sportfy.exeptions.OutroUsuarioComDadosJaExistentes;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Permissao;
import com.sportfy.sportfy.models.Usuario;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.PermissaoRepository;
import com.sportfy.sportfy.repositories.UsuarioRepository;
import com.sportfy.sportfy.util.EnviarEmail;
import com.sportfy.sportfy.util.GeraSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private UsuarioRepository usuarioRepository;

    @Autowired
    AcademicoRepository academicoRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public AcademicoDto cadastrar(AcademicoDto academicoDto) throws EmailInvalidoException, UsuarioJaExisteException, PermissaoNaoExisteException {
        if (isEmailFromUfpr(academicoDto.email())) {
            Optional<Usuario> existUsuario = usuarioRepository.findByUsernameOrEmailOrCpf(academicoDto.username(), academicoDto.email(), academicoDto.cpf());
            if (!existUsuario.isPresent()) {
                Optional<Permissao> permissao = permissaoRepository.findByTipoPermissao(TipoPermissao.ACADEMICO);
                if (permissao.isPresent()) {
                    Academico novoAcademico = new Academico();
                    String senha = GeraSenha.generatePassword();
                    novoAcademico.cadastrar(academicoDto);
                    novoAcademico.getUsuario().setPassword(passwordEncoder.encode(senha));
                    novoAcademico.getUsuario().setPermissao(permissao.get());
                    Academico academicoCriado = academicoRepository.save(novoAcademico);
                    try {
                        String mensagem = "Bem vindo ao Sportfy! acesse a sua conta com o username cadastrado e a sua senha: \n\n" + senha +
                        "\n\n\nBora se movimentar e fazer amigos?\n o Sportfy irá te ajudar!";
                        email.sendEmail(academicoCriado.getUsuario().getEmail(), "Sportfy seu app de eventos esportivos!", mensagem);
                    } catch (Exception e) {
                        System.err.println("Erro ao enviar email: " + e.getMessage());
                    }
                    return AcademicoDto.fromAcademicoBD(academicoCriado);
                } else {
                    throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", TipoPermissao.ACADEMICO));
                }
            } else {
                throw new UsuarioJaExisteException("Usuario ja existe!");
            }
        } else {
            throw new EmailInvalidoException("Email invalido!");
        }
    }

    public AcademicoDto atualizar(Long idAcademico, AcademicoDto academicoDto) throws EmailInvalidoException, AcademicoNaoExisteException, OutroUsuarioComDadosJaExistentes {
        if (isEmailFromUfpr(academicoDto.email())) {
            Optional<Academico> academicoBD = academicoRepository.findById(idAcademico);
            if (academicoBD.isPresent()) {
                Optional<Academico> academicoExistente = academicoRepository.findByUsuarioUsernameOrUsuarioEmailOrUsuarioCpf(academicoDto.username(), academicoDto.email(), academicoDto.cpf());
                if (academicoExistente.isPresent() && academicoExistente.get().getUsuario().getIdUsuario().equals(academicoBD.get().getUsuario().getIdUsuario())) {
                    Academico academicoAtualizado = new Academico();
                    academicoAtualizado.atualizar(academicoBD.get().getIdAcademico(), academicoBD.get().getUsuario().getIdUsuario(), academicoDto);
                    academicoAtualizado.getUsuario().setPermissao(academicoBD.get().getUsuario().getPermissao());
                    if (academicoDto.password() == null || academicoDto.password().isEmpty()) {
                        academicoAtualizado.getUsuario().setPassword(academicoBD.get().getUsuario().getPassword());
                    } else {
                        academicoAtualizado.getUsuario().setPassword(passwordEncoder.encode(academicoDto.password()));
                    }
                    Academico academicoSalvo = academicoRepository.save(academicoAtualizado);
                    return AcademicoDto.fromAcademicoBD(academicoSalvo);
                } else {
                    throw new OutroUsuarioComDadosJaExistentes("Outro usuário com esses dados já existente!");
                }
            } else {
                throw new AcademicoNaoExisteException("Academico não existe!");
            }
        } else {
            throw new EmailInvalidoException("Email invalido!");
        }
    }

    public AcademicoDto inativar(Long idAcademico) throws AcademicoNaoExisteException {
        return academicoRepository.findById(idAcademico).map(academicoBD -> {
            academicoBD.getUsuario().inativar();
            Academico academicoInativado = academicoRepository.save(academicoBD);
            return AcademicoDto.fromAcademicoBD(academicoInativado);
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

}
