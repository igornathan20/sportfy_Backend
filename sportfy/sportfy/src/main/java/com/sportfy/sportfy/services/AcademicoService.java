package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.EmailInvalidoException;
import com.sportfy.sportfy.exeptions.ListaAcademicosVaziaException;
import com.sportfy.sportfy.exeptions.OutroUsuarioComDadosJaExistentes;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Permissao;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.PermissaoRepository;
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
    AcademicoRepository academicoRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

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
    
        if (!existAcademicoBD.isPresent()) {
            Academico novoAcademico = new Academico();
            String senha = GeraSenha.generatePassword();
            novoAcademico.cadastrar(academicoDto);
            novoAcademico.getUsuario().setPassword(passwordEncoder.encode(senha));
            novoAcademico.getUsuario().setPermissao(permissao.get());
            Academico academicoCriado = academicoRepository.save(novoAcademico);
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

}
