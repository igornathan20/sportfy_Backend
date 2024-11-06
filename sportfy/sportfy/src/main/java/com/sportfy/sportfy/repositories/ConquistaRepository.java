package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.sportfy.sportfy.models.Conquista;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {
    Optional<Conquista> findByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportiva(Long idAcademico, Long idMetaEsportiva);
    List<Conquista> findByAcademicoIdAcademico(Long idAcademico);
    @Query("SELECT c FROM Conquista c WHERE c.academico.idAcademico = :idAcademico AND c.metaEsportiva.idMetaEsportiva IN :idMetaEsportivas")
    List<Conquista> findByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportivaIn(Long idAcademico, List<Long> idMetaEsportivas);
}
