package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.dtos.UserResponseDto;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.Roles;
import com.sportfy.sportfy.repositories.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto novoAdministrador(AdministradorDto admDto) throws UsuarioJaExisteException, RoleNaoPermitidaException {
        Administrador existAdm = administradorRepository.findByUsername(admDto.username());

        if (existAdm != null) {
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }

        Administrador novoAdministrador = new Administrador();
        novoAdministrador.setUsername(admDto.username());
        novoAdministrador.setEmail(admDto.email());
        novoAdministrador.setPassword(passwordEncoder.encode(admDto.password()));
        novoAdministrador.setNome(admDto.nome());
        novoAdministrador.setCpf(admDto.cpf());
        novoAdministrador.setTelefone(admDto.telefone());
        novoAdministrador.setDataCriacao(LocalDate.now());

        switch (admDto.userRole()){
            case 2:
                novoAdministrador.setUserRole(Roles.ADMIN);
                break;
            case 3:
                novoAdministrador.setUserRole(Roles.MASTER);
                break;
            default:
                throw new RoleNaoPermitidaException("Role não permitida!");
        }

        Administrador createdUser = administradorRepository.save(novoAdministrador);
        return new UserResponseDto(createdUser.getId(), createdUser.getUsername(), "ROLE_" + createdUser.getUserRole().getRole());
    }
}
