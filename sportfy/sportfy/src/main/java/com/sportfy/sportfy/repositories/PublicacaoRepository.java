package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Publicacao;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
    
}
