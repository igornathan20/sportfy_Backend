package com.sportfy.sportfy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador,Long> {
    Optional<Administrador> findByUsuarioIdUsuario(Long idUsuario);
    Optional<Administrador> findByUsuarioUsername(String username);
    Optional<Administrador> findByUsuarioEmail(String email);
    Optional<Administrador> findByUsuarioCpf(String cpf);
    Optional<Administrador> findByUsuarioUsernameOrUsuarioEmailOrUsuarioCpf(String username, String email, String cpf);
}
