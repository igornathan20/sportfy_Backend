package com.sportfy.sportfy.repositories;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.Academico;

@Repository
public interface AcademicoRepository extends JpaRepository<Academico, Long> {
    Optional<Academico> findByIdAcademicoAndUsuarioAtivo(Long idAcademico, boolean ativo);
    Optional<Academico> findByEmail(String email);
    Optional<Academico> findByEmailAndUsuarioAtivo(String email, boolean ativo);
    Optional<Academico> findByUsuarioIdUsuario(Long idUsuario);
    Optional<Academico> findByUsuarioIdUsuarioAndUsuarioAtivo(Long idUsuario, boolean ativo);
    Optional<Academico> findByUsuarioUsername(String username);
    Optional<Academico> findByUsuarioUsernameAndUsuarioAtivo(String username, boolean ativo);
    Page<Academico> findByUsuarioAtivo(boolean ativo, Pageable pageable);
    Optional<List<Academico>> findByUsuarioUsernameOrEmail(String username, String email);
    Optional<List<Academico>> findByUsuarioNomeContainingIgnoreCase(String nome);
    Optional<List<Academico>> findByUsuarioUsernameContainingIgnoreCase(String username);
    Optional<List<Academico>> findByUsuarioNomeContainingIgnoreCaseOrUsuarioUsernameContainingIgnoreCase(String nome, String username);
    @Query("SELECT a FROM Academico a WHERE a.usuario.dataCriacao BETWEEN :dataInicio AND CURRENT_TIMESTAMP")
    List<Academico> findByUsuarioDataCriacaoInLast30Days(@Param("dataInicio") OffsetDateTime dataInicio);
}
