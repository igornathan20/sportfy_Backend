package com.sportfy.sportfy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Academico;

@Repository
public interface AcademicoRepository extends JpaRepository<Academico, Long> {
    Optional<Academico> findByUsuarioIdUsuario(Long idUsuario);
    Optional<Academico> findByUsuarioUsername(String username);
    Optional<Academico> findByUsuarioEmail(String email);
    Optional<Academico> findByUsuarioCpf(String cpf);
    Optional<Academico> findByUsuarioUsernameOrUsuarioEmailOrUsuarioCpf(String username, String email, String cpf);
    Optional<List<Academico>> findByUsuarioAtivo(boolean ativo);
}
