package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.dtos.AdministradorResponseDto;
import com.sportfy.sportfy.enums.TipoCanal;
import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaAdministradoresVaziaException;
import com.sportfy.sportfy.exeptions.OutroUsuarioComDadosJaExistentes;
import com.sportfy.sportfy.exeptions.PasswordInvalidoException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.UsuarioCanal;
import com.sportfy.sportfy.repositories.AdministradorRepository;
import com.sportfy.sportfy.repositories.CanalRepository;
import com.sportfy.sportfy.repositories.UsuarioCanalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private UsuarioCanalRepository usuarioCanalRepository;
    @Autowired
    private CanalRepository canalRepository;

    public AdministradorResponseDto cadastrar(AdministradorDto administradorDto) throws PasswordInvalidoException, OutroUsuarioComDadosJaExistentes, RoleNaoPermitidaException, PermissaoNaoExisteException {
        if (isPasswordValid(administradorDto.password())) {
            throw new PasswordInvalidoException("Password não pode ser nulo ou vazio!");
        }

        Optional<Administrador> existAdministradorBD = administradorRepository.findByUsuarioUsername(administradorDto.username());
        if (!existAdministradorBD.isPresent() || !existAdministradorBD.get().getUsuario().isAtivo()) {
                if (!existAdministradorBD.isPresent()) {
                    Administrador novoAdministrador = new Administrador();
                    novoAdministrador.cadastrar(administradorDto);
                    novoAdministrador.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
                    novoAdministrador.getUsuario().setPermissao(TipoPermissao.ADMINISTRADOR);
                    Administrador administradorCriado = administradorRepository.save(novoAdministrador);
                    UsuarioCanal usuarioCanal = new UsuarioCanal();
                    usuarioCanal.setUsuario(administradorCriado.getUsuario());
                    usuarioCanal.setCanal(canalRepository.findByTipoCanal(TipoCanal.COMUNIDADE));
                    usuarioCanalRepository.save(usuarioCanal);
                    return AdministradorResponseDto.fromEntity(administradorCriado);
                } else {
                    Administrador administradorAtualizado = new Administrador();
                    administradorAtualizado.atualizar(existAdministradorBD.get().getIdAdministrador(), existAdministradorBD.get().getUsuario().getIdUsuario(), administradorDto);
                    administradorAtualizado.getUsuario().setPermissao(TipoPermissao.ADMINISTRADOR);
                    if (administradorDto.password() == null || administradorDto.password().isEmpty()) {
                        administradorAtualizado.getUsuario().setPassword(existAdministradorBD.get().getUsuario().getPassword());
                    } else {
                        administradorAtualizado.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
                    }
                    Administrador administradorSalvo = administradorRepository.save(administradorAtualizado);
                    return AdministradorResponseDto.fromEntity(administradorSalvo);
                }
        } else {
            throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username já existente!");
        }
    }

    public AdministradorResponseDto atualizar(Long idAdministrador, AdministradorDto administradorDto, String usuarioAutenticado) throws AdministradorNaoExisteException, AccessDeniedException, OutroUsuarioComDadosJaExistentes, RoleNaoPermitidaException, PermissaoNaoExisteException {
        Optional<Administrador> administradorBD = administradorRepository.findByIdAdministradorAndUsuarioAtivo(idAdministrador, true);
        if (administradorBD.isPresent()) {
            if (administradorBD.get().getUsuario().getUsername().equals(usuarioAutenticado)){
                Optional<Administrador> administradorExistente = administradorRepository.findByUsuarioUsername(administradorDto.username());
                if (!administradorExistente.isPresent() || administradorExistente.get().getUsuario().getIdUsuario().equals(administradorBD.get().getUsuario().getIdUsuario())) {
                    Administrador administradorAtualizado = new Administrador();
                    administradorAtualizado.atualizar(administradorBD.get().getIdAdministrador(), administradorBD.get().getUsuario().getIdUsuario(), administradorDto);
                    administradorAtualizado.getUsuario().setPermissao(TipoPermissao.ADMINISTRADOR);
                    if (administradorDto.password() == null || administradorDto.password().isEmpty()) {
                        administradorAtualizado.getUsuario().setPassword(administradorBD.get().getUsuario().getPassword());
                    } else {
                        administradorAtualizado.getUsuario().setPassword(passwordEncoder.encode(administradorDto.password()));
                    }
                    Administrador administradorSalvo = administradorRepository.save(administradorAtualizado);
                    return AdministradorResponseDto.fromEntity(administradorSalvo);
                } else {
                    throw new OutroUsuarioComDadosJaExistentes("Outro usuário com username já existente!");
                }
            }else {
                throw new AccessDeniedException("Voce não tem permissão para alterar esse recurso!");
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

    public Page<AdministradorResponseDto> listar(Pageable pageable) throws ListaAdministradoresVaziaException {
        Page<Administrador> listaAdministradorBD = administradorRepository.findByUsuarioAtivo(true, pageable);
        if (!listaAdministradorBD.isEmpty()) {
            Page<AdministradorResponseDto> listaAdministradorDto = listaAdministradorBD.map(AdministradorResponseDto::fromEntity);
            return listaAdministradorDto;
        } else {
            throw new ListaAdministradoresVaziaException("Lista de administradores vazia!");
        }
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.isBlank();
    }

}
