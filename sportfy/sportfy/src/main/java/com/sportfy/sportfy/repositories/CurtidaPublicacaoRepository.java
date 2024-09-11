package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.CurtidaPublicacao;

@Repository
public interface CurtidaPublicacaoRepository extends JpaRepository<CurtidaPublicacao, Long> {

}
