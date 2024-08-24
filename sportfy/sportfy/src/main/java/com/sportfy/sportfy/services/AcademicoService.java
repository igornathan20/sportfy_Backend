package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.dtos.UserResponseDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.EmailInvalidoException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Permissao;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.PermissaoRepository;
import com.sportfy.sportfy.util.EnviarEmail;
import com.sportfy.sportfy.util.GeraSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcademicoService {

    @Autowired
    AcademicoRepository academicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnviarEmail email;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public UserResponseDto novoAcademico(AcademicoDto academicoDto) throws UsuarioJaExisteException, EmailInvalidoException, PermissaoNaoExisteException {
        Academico existUser = academicoRepository.findByUsername(academicoDto.username());

        if (existUser != null) {
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }

        if (isEmailFromUfpr(academicoDto.email())) {
            Optional<Permissao> permissao = permissaoRepository.findByTipoPermissao(TipoPermissao.ACADEMICO);
            if (permissao.isPresent()) {
                Academico novoUsuario = new Academico();
                novoUsuario.setUsername(academicoDto.username());
                novoUsuario.setEmail(academicoDto.email());
                String senha = GeraSenha.generatePassword();
                novoUsuario.setPassword(passwordEncoder.encode(senha));
                novoUsuario.setNome(academicoDto.nome());
                novoUsuario.setCpf(academicoDto.cpf());
                novoUsuario.setPermissao(permissao.get());
                Academico createdUser = academicoRepository.save(novoUsuario);

                try {
                    String mensagem = "Bem vindo ao Sportfy! acesse a sua conta com o username cadastrado e a sua senha: \n\n" + senha +
                    "\n\n\nBora se movimentar e fazer amigos?\n o Sportfy irá te ajudar!";
                    email.sendEmail(academicoDto.email(), "Sportfy seu app de eventos esportivos!", mensagem);
                } catch (Exception e) {
                    System.err.println("Erro ao enviar email: " + e.getMessage());
                }
                return new UserResponseDto(createdUser.getIdUsuario(), createdUser.getUsername(), "ROLE_" + createdUser.getPermissao().getTipoPermissao().name());
            } else {
                throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", TipoPermissao.ACADEMICO));
            }
        } else {
            throw new EmailInvalidoException("Email invalido!");
        }
    }

    public boolean isEmailFromUfpr(String email) {
        return email != null && email.endsWith("@ufpr.br");
    }
}
