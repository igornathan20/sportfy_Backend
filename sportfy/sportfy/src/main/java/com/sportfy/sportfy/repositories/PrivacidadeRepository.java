package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Privacidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacidadeRepository extends JpaRepository<Privacidade, Long> {
    Privacidade findByIdAcademico(Long idAcademico);
}
