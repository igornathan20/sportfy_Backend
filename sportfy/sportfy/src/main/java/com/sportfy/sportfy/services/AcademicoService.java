package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.dtos.UserResponseDto;
import com.sportfy.sportfy.exeptions.EmailInvalidoException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Roles;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.util.EnviarEmail;
import com.sportfy.sportfy.util.GeraSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AcademicoService {

    @Autowired
    AcademicoRepository academicoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EnviarEmail email;

    public UserResponseDto novoAcademico(AcademicoDto academicoDto) throws UsuarioJaExisteException,EmailInvalidoException {
        Academico existUser = academicoRepository.findByUsername(academicoDto.username());

        if (existUser != null) {
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }

        if (isEmailFromUfpr(academicoDto.email())) {
            Academico novoUsuario = new Academico();
            novoUsuario.setUsername(academicoDto.username());
            novoUsuario.setEmail(academicoDto.email());
            novoUsuario.setNome(academicoDto.nome());
            novoUsuario.setCpf(academicoDto.cpf());
            novoUsuario.setTelefone(academicoDto.telefone());
            novoUsuario.setDataCriacao(LocalDate.now());
            novoUsuario.setUserRole(Roles.ACADEMICO);
            String senha = GeraSenha.generatePassword();
            novoUsuario.setPassword(passwordEncoder.encode(senha));

            Academico createdUser = academicoRepository.save(novoUsuario);

            try {
                String mensagem = "Bem vindo ao Sportfy! acesse a sua conta com o username cadastrado e a sua senha: \n\n" + senha +
                        "\n\n\nBora se movimentar e fazer amigos?\n o Sportfy irá te ajudar!";
                email.sendEmail(academicoDto.email(), "Sportfy seu app de eventos esportivos!", mensagem);
            } catch (Exception e) {
                System.err.println("Erro ao enviar email: " + e.getMessage());
            }
            return new UserResponseDto(createdUser.getId(), createdUser.getUsername(), "ROLE_" + createdUser.getUserRole().getRole());
        }
        throw new EmailInvalidoException("Email invalido!");
    }

    public boolean isEmailFromUfpr(String email) {
        return email != null && email.endsWith("@ufpr.br");
    }
}
