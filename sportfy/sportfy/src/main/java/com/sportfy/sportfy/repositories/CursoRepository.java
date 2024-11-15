package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}