package com.sportfy.sportfy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.Comentario;
import jakarta.transaction.Transactional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Comentario c WHERE c.idComentario = :idComentario")
    void deleteComentarioById(@Param("idComentario") Long idComentario);
    Page<Comentario> findByPublicacaoIdPublicacao(Long idPublicacao, Pageable pageable);
}
