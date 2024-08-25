package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaAdministradoresVaziaException;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public AdministradorDto cadastrar(AdministradorDto administradorDto) throws UsuarioJaExisteException, RoleNaoPermitidaException, PermissaoNaoExisteException {
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
            Administrador administradorCriado = administradorRepository.save(novoAdministrador);
            return AdministradorDto.fromAdministradorBD(administradorCriado);
        } else {
            throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", administradorDto.permissao()));
        }
    }

    public AdministradorDto inativar(Long idAdministrador) throws AdministradorNaoExisteException {
        return administradorRepository.findById(idAdministrador).map(administradorBD -> {
            administradorBD.getUsuario().inativar();
            Administrador administradorInativado = administradorRepository.save(administradorBD);
            return AdministradorDto.fromAdministradorBD(administradorInativado);
        }).orElseThrow(() -> new AdministradorNaoExisteException("Administrador não existe!"));
    }

    public List<AdministradorDto> listar() throws ListaAdministradoresVaziaException {
        Optional<List<Administrador>> listaAdministradorBD = administradorRepository.findByUsuarioAtivo(true);
        if (listaAdministradorBD.isPresent()) {
            List<AdministradorDto> listaAdministradorDto = listaAdministradorBD.get().stream().map(administradorBD -> AdministradorDto.fromAdministradorBD(administradorBD)).collect(Collectors.toList());
            return listaAdministradorDto;
        } else {
            throw new ListaAdministradoresVaziaException("Lista de administradores vazia!");
        }
    }

}
