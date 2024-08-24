package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicoRepository extends JpaRepository<Academico, Long> {
    Academico findByUsername(String username);
}
