package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.CurtidaPublicacao;
import jakarta.transaction.Transactional;

@Repository
public interface CurtidaPublicacaoRepository extends JpaRepository<CurtidaPublicacao, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM CurtidaPublicacao cp WHERE cp.idCurtidaPublicacao = :idCurtidaPublicacao")
    void deleteCurtidaById(@Param("idCurtidaPublicacao") Long idCurtidaPublicacao);
}
