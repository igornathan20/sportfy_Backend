package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.CurtidaComentario;

@Repository
public interface CurtidaComentarioRepository extends JpaRepository<CurtidaComentario, Long> {

}
