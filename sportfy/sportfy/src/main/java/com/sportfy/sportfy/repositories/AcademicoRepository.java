package com.sportfy.sportfy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Academico;

@Repository
public interface AcademicoRepository extends JpaRepository<Academico, Long> {
    Optional<Academico> findByUsuarioIdUsuarioAndUsuarioAtivo(Long idUsuario, boolean ativo);
    Optional<Academico> findByUsuarioUsernameAndUsuarioAtivo(String username, boolean ativo);
    Optional<Academico> findByUsuarioEmailAndUsuarioAtivo(String email, boolean ativo);
    Optional<Academico> findByUsuarioCpfAndUsuarioAtivo(String cpf, boolean ativo);
    Optional<Academico> findByIdAcademicoAndUsuarioAtivo(Long idAcademico, boolean ativo);
    Optional<List<Academico>> findByUsuarioUsernameOrUsuarioEmailOrUsuarioCpf(String username, String email, String cpf);
    Optional<List<Academico>> findByUsuarioAtivo(boolean ativo);
}
