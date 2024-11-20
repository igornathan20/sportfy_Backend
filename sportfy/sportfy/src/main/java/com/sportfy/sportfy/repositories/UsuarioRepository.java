package com.sportfy.sportfy.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByUsernameAndAtivo(String username, boolean ativo);
    Optional<Usuario> findByIdUsuarioAndAtivo(Long idUsuario, boolean ativo);
    Optional<List<Usuario>> findByAtivo(boolean ativo);
    Optional<List<Usuario>> findByNomeContainingIgnoreCase(String nome);
    Optional<List<Usuario>> findByUsernameContainingIgnoreCase(String username);
    Optional<List<Usuario>> findByNomeContainingIgnoreCaseOrUsernameContainingIgnoreCase(String nome, String username);
}
