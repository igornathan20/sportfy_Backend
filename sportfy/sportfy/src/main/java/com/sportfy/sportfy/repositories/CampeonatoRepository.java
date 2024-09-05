package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>, JpaSpecificationExecutor<Campeonato> {
    boolean existsByCodigo(String codigo);
}
