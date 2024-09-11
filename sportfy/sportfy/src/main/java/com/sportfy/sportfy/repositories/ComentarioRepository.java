package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
