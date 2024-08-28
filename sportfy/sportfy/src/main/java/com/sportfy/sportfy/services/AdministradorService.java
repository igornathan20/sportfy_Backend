package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaAdministradoresVaziaException;
import com.sportfy.sportfy.exeptions.OutroUsuarioComDadosJaExistentes;
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
        Optional<List<Usuario>> existUsuarioBD = usuarioRepository.findByUsernameOrEmailOrCpf(administradorDto.username(), administradorDto.email(), administradorDto.cpf());
        if (!existUsuarioBD.isPresent()) {
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
        } else {
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }
    }

    public AdministradorDto atualizar(Long idAdministrador, AdministradorDto administradorDto) throws AdministradorNaoExisteException, OutroUsuarioComDadosJaExistentes, RoleNaoPermitidaException, PermissaoNaoExisteException {
        Optional<Administrador> administradorBD = administradorRepository.findByIdAdministradorAndUsuarioAtivo(idAdministrador, true);
        if (administradorBD.isPresent()) {
            Optional<List<Administrador>> administradorExistente = administradorRepository.findByUsuarioUsernameOrUsuarioEmailOrUsuarioCpf(administradorDto.username(), administradorDto.email(), administradorDto.cpf());
            if (administradorExistente.isPresent() && administradorExistente.get().size() == 1 && administradorExistente.get().get(0).getUsuario().getIdUsuario().equals(administradorBD.get().getUsuario().getIdUsuario())) {
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
                    Administrador administradorAtualizado = new Administrador();
                    administradorAtualizado.atualizar(administradorBD.get().getIdAdministrador(), administradorBD.get().getUsuario().getIdUsuario(), administradorDto);
                    administradorAtualizado.getUsuario().setPermissao(permissao.get());
                    if (administradorDto.password() == null || administradorDto.password().isEmpty()) {
                        administradorAtualizado.getUsuario().setPassword(administradorBD.get().getUsuario().getPassword());
                    } else {
                        administradorAtualizado.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
                    }
                    Administrador administradorSalvo = administradorRepository.save(administradorAtualizado);
                    return AdministradorDto.fromAdministradorBD(administradorSalvo);
                } else {
                    throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", administradorDto.permissao()));
                }
            } else {
                throw new OutroUsuarioComDadosJaExistentes("Outro usuário com esses dados já existente!");
            }
        } else {
            throw new AdministradorNaoExisteException("Administrador não existe!");
        }
    }

    public AdministradorDto inativar(Long idAdministrador) throws AdministradorNaoExisteException {
        return administradorRepository.findByIdAdministradorAndUsuarioAtivo(idAdministrador, true).map(administradorBD -> {
            administradorBD.getUsuario().inativar();
            Administrador administradorInativado = administradorRepository.save(administradorBD);
            return AdministradorDto.fromAdministradorBD(administradorInativado);
        }).orElseThrow(() -> new AdministradorNaoExisteException("Administrador não existe!"));
    }

    public AdministradorDto consultar(Long idUsuario) throws AdministradorNaoExisteException {
        return administradorRepository.findByUsuarioIdUsuarioAndUsuarioAtivo(idUsuario, true).map(administradorBD -> {
            return AdministradorDto.fromAdministradorBD(administradorBD);
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
