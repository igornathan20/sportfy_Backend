package com.sportfy.sportfy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
    Usuario findByCpf(String email);
    Optional<Usuario> findByUsernameOrEmailOrCpf(String username, String email, String cpf);
}
