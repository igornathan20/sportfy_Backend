package com.sportfy.sportfy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Optional<Usuario> findByUsernameAndAtivo(String username, boolean ativo);
    Optional<Usuario> findByEmailAndAtivo(String email, boolean ativo);
    Optional<Usuario> findByCpfAndAtivo(String email, boolean ativo);
    Optional<Usuario> findByUsernameOrEmailOrCpf(String username, String email, String cpf);
    Optional<List<Usuario>> findByAtivo(boolean ativo);
}
