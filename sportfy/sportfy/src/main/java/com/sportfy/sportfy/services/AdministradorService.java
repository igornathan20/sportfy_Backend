package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.dtos.AdministradorResponseDto;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaAdministradoresVaziaException;
import com.sportfy.sportfy.exeptions.OutroUsuarioComDadosJaExistentes;
import com.sportfy.sportfy.exeptions.PasswordInvalidoException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.Permissao;
import com.sportfy.sportfy.repositories.AdministradorRepository;
import com.sportfy.sportfy.repositories.PermissaoRepository;

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
    private AdministradorRepository administradorRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public AdministradorResponseDto cadastrar(AdministradorDto administradorDto) throws PasswordInvalidoException, OutroUsuarioComDadosJaExistentes, RoleNaoPermitidaException, PermissaoNaoExisteException {
        if (isPasswordValid(administradorDto.password())) {
            throw new PasswordInvalidoException("Password não pode ser nulo ou vazio!");
        }

        Optional<Administrador> existAdministradorBD = administradorRepository.findByUsuarioUsername(administradorDto.username());
        if (!existAdministradorBD.isPresent() || !existAdministradorBD.get().getUsuario().isAtivo()) {
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
                if (!existAdministradorBD.isPresent()) {
                    Administrador novoAdministrador = new Administrador();
                    novoAdministrador.cadastrar(administradorDto);
                    novoAdministrador.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
                    novoAdministrador.getUsuario().setPermissao(permissao.get());
                    Administrador administradorCriado = administradorRepository.save(novoAdministrador);
                    return AdministradorResponseDto.fromEntity(administradorCriado);
                } else {
                    Administrador administradorAtualizado = new Administrador();
                    administradorAtualizado.atualizar(existAdministradorBD.get().getIdAdministrador(), existAdministradorBD.get().getUsuario().getIdUsuario(), administradorDto);
                    administradorAtualizado.getUsuario().setPermissao(permissao.get());
                    if (administradorDto.password() == null || administradorDto.password().isEmpty()) {
                        administradorAtualizado.getUsuario().setPassword(existAdministradorBD.get().getUsuario().getPassword());
                    } else {
                        administradorAtualizado.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
                    }
                    Administrador administradorSalvo = administradorRepository.save(administradorAtualizado);
                    return AdministradorResponseDto.fromEntity(administradorSalvo);
                }
            } else {
                throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", administradorDto.permissao()));
            }
        } else {
            throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username já existente!");
        }
    }

    public AdministradorResponseDto atualizar(Long idAdministrador, AdministradorDto administradorDto) throws AdministradorNaoExisteException, OutroUsuarioComDadosJaExistentes, RoleNaoPermitidaException, PermissaoNaoExisteException {
        Optional<Administrador> administradorBD = administradorRepository.findByIdAdministradorAndUsuarioAtivo(idAdministrador, true);
        if (administradorBD.isPresent()) {
            Optional<Administrador> administradorExistente = administradorRepository.findByUsuarioUsername(administradorDto.username());
            if (!administradorExistente.isPresent() || administradorExistente.get().getUsuario().getIdUsuario().equals(administradorBD.get().getUsuario().getIdUsuario())) {
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
                    return AdministradorResponseDto.fromEntity(administradorSalvo);
                } else {
                    throw new PermissaoNaoExisteException(String.format("Permissao %s nao existe no banco de dados!", administradorDto.permissao()));
                }
            } else {
                throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username já existente!");
            }
        } else {
            throw new AdministradorNaoExisteException("Administrador não existe!");
        }
    }

    public AdministradorResponseDto inativar(Long idAdministrador) throws AdministradorNaoExisteException {
        return administradorRepository.findByIdAdministradorAndUsuarioAtivo(idAdministrador, true).map(administradorBD -> {
            administradorBD.getUsuario().inativar();
            Administrador administradorInativado = administradorRepository.save(administradorBD);
            return AdministradorResponseDto.fromEntity(administradorInativado);
        }).orElseThrow(() -> new AdministradorNaoExisteException("Administrador não existe!"));
    }

    public AdministradorResponseDto consultar(Long idUsuario) throws AdministradorNaoExisteException {
        return administradorRepository.findByIdAdministradorAndUsuarioAtivo(idUsuario, true).map(administradorBD -> {
            return AdministradorResponseDto.fromEntity(administradorBD);
        }).orElseThrow(() -> new AdministradorNaoExisteException("Administrador não existe!"));
    }

    public List<AdministradorResponseDto> listar() throws ListaAdministradoresVaziaException {
        Optional<List<Administrador>> listaAdministradorBD = administradorRepository.findByUsuarioAtivo(true);
        if (listaAdministradorBD.isPresent()) {
            List<AdministradorResponseDto> listaAdministradorDto = listaAdministradorBD.get().stream().map(administradorBD -> AdministradorResponseDto.fromEntity(administradorBD)).collect(Collectors.toList());
            return listaAdministradorDto;
        } else {
            throw new ListaAdministradoresVaziaException("Lista de administradores vazia!");
        }
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.isBlank();
    }

}
