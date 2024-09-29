package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Regra;

import java.util.*;

@Repository
public interface RegraRepository extends JpaRepository<Regra, Long> {
    List<Regra> findByModalidadeEsportivaIdModalidadeEsportiva(Long idModalidadeEsportiva);

}
