package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.sportfy.sportfy.models.Conquista;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {
    Optional<Conquista> findByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportiva(Long idAcademico, Long idMetaEsportiva);
    List<Conquista> findByAcademicoIdAcademico(Long idAcademico);
}
