package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.dtos.UserResponseDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.Permissao;
import com.sportfy.sportfy.repositories.AdministradorRepository;
import com.sportfy.sportfy.repositories.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public UserResponseDto novoAdministrador(AdministradorDto admDto) throws UsuarioJaExisteException, RoleNaoPermitidaException, PermissaoNaoExisteException {
        Administrador existAdm = administradorRepository.findByUsername(admDto.username());

        if (existAdm != null) {
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }

        Optional<Permissao> permissao = Optional.empty();
        switch (admDto.permissao()) {
            case TipoPermissao.ADMINISTRADOR:
                permissao = permissaoRepository.findByTipoPermissao(TipoPermissao.ADMINISTRADOR);
                break;
            case TipoPermissao.ADMINISTRADOR_MASTER:
                permissao = permissaoRepository.findByTipoPermissao(TipoPermissao.ADMINISTRADOR_MASTER);
                break;
            default:
                throw new RoleNaoPermitidaException("Role não permitida!");
        }

        if (permissao.isPresent()) {
            Administrador novoAdministrador = new Administrador();
            novoAdministrador.setUsername(admDto.username());
            novoAdministrador.setEmail(admDto.email());
            novoAdministrador.setPassword(passwordEncoder.encode(admDto.password()));
            novoAdministrador.setNome(admDto.nome());
            novoAdministrador.setCpf(admDto.cpf());
            novoAdministrador.setTelefone(admDto.telefone());
            novoAdministrador.setDataNascimento(admDto.dataNascimento());
            novoAdministrador.setFoto(admDto.foto());

            Administrador createdUser = administradorRepository.save(novoAdministrador);
            return new UserResponseDto(createdUser.getIdUsuario(), createdUser.getUsername(), "ROLE_" + createdUser.getPermissao().getTipoPermissao().name());
        } else {
            throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", admDto.permissao()));
        }
    }

}
