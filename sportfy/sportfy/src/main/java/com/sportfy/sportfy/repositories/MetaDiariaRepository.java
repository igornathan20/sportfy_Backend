package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.MetaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetaDiariaRepository extends JpaRepository<MetaDiaria, Long> {
    List<MetaDiaria> findByTituloContainingIgnoreCaseAndAcademico(String nome, Academico academico);
    List<MetaDiaria> findByAcademico(Academico academico);
}
