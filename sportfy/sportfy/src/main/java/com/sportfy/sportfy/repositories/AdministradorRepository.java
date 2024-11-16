package com.sportfy.sportfy.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador,Long> {
    Optional<Administrador> findByIdAdministradorAndUsuarioAtivo(Long idAdministrador, boolean ativo);
    Optional<Administrador> findByUsuarioIdUsuario(Long idUsuario);
    Optional<Administrador> findByUsuarioIdUsuarioAndUsuarioAtivo(Long idUsuario, boolean ativo);
    Optional<Administrador> findByUsuarioUsername(String username);
    Optional<Administrador> findByUsuarioUsernameAndUsuarioAtivo(String username, boolean ativo);
    Page<Administrador> findByUsuarioAtivo(boolean ativo, Pageable pageable);
    Optional<List<Administrador>> findByUsuarioNomeContainingIgnoreCase(String nome);
    Optional<List<Administrador>> findByUsuarioUsernameContainingIgnoreCase(String username);
    Optional<List<Administrador>> findByUsuarioNomeContainingIgnoreCaseOrUsuarioUsernameContainingIgnoreCase(String nome, String username);
}
