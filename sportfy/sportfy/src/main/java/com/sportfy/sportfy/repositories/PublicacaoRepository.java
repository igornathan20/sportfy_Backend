package com.sportfy.sportfy.repositories;

import java.time.OffsetDateTime;
import java.util.List;
import com.sportfy.sportfy.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.Publicacao;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
    Page<Publicacao> findByCanalIdCanal(Long idCanal, Pageable pageable);
    Page<Publicacao> findByCanalIdCanalAndUsuarioUsername(Long idCanal, String username, Pageable pageable);
    @Query("SELECT p FROM Publicacao p WHERE p.dataPublicacao BETWEEN :dataInicio AND CURRENT_TIMESTAMP")
    List<Publicacao> findByDataPublicacaoInLast30Days(@Param("dataInicio") OffsetDateTime dataInicio);
    List<Publicacao> findByUsuario(Usuario usuario);
}
