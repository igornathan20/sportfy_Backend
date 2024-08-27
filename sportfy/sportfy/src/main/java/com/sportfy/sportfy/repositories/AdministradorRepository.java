package com.sportfy.sportfy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador,Long> {
    Optional<Administrador> findByUsuarioIdUsuarioAndUsuarioAtivo(Long idUsuario, boolean ativo);
    Optional<Administrador> findByUsuarioUsernameAndUsuarioAtivo(String username, boolean ativo);
    Optional<Administrador> findByUsuarioEmailAndUsuarioAtivo(String email, boolean ativo);
    Optional<Administrador> findByUsuarioCpfAndUsuarioAtivo(String cpf, boolean ativo);
    Optional<Administrador> findByIdAdministradorAndUsuarioAtivo(Long idAdministrador, boolean ativo);
    Optional<Administrador> findByUsuarioUsernameOrUsuarioEmailOrUsuarioCpf(String username, String email, String cpf);
    Optional<List<Administrador>> findByUsuarioAtivo(boolean ativo);
}
