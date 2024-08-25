package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.dtos.UserResponseDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.Permissao;
import com.sportfy.sportfy.models.Usuario;
import com.sportfy.sportfy.repositories.AdministradorRepository;
import com.sportfy.sportfy.repositories.PermissaoRepository;
import com.sportfy.sportfy.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public UserResponseDto cadastrar(AdministradorDto administradorDto) throws UsuarioJaExisteException, RoleNaoPermitidaException, PermissaoNaoExisteException {
        Optional<Usuario> existUsuario = usuarioRepository.findByUsernameOrEmailOrCpf(administradorDto.username().toLowerCase(), administradorDto.email().toLowerCase(), administradorDto.cpf());
        if (existUsuario.isPresent()) {
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }

        Optional<Permissao> permissao = Optional.empty();
        switch (administradorDto.permissao()) {
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
            novoAdministrador.cadastrar(administradorDto);
            novoAdministrador.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
            novoAdministrador.getUsuario().setPermissao(permissao.get());
            Administrador createdUser = administradorRepository.save(novoAdministrador);
            
            return new UserResponseDto(createdUser.getUsuario().getIdUsuario(), createdUser.getUsuario().getUsername(), "ROLE_" + createdUser.getUsuario().getPermissao().getTipoPermissao().name());
        } else {
            throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", administradorDto.permissao()));
        }
    }

    public AdministradorDto inativar(Long idAdministrador) throws AdministradorNaoExisteException {
        return administradorRepository.findById(idAdministrador).map(administradorBD -> {
            administradorBD.getUsuario().inativar();
            return AdministradorDto.fromAdministradorBD(administradorRepository.save(administradorBD));
        }).orElseThrow(() -> new AdministradorNaoExisteException("Administrador não existe!"));
    }

}
