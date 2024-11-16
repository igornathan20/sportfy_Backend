package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.enums.TipoCanal;
import com.sportfy.sportfy.models.Canal;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {
    Canal findByTipoCanal(TipoCanal tipoCanal);
}
