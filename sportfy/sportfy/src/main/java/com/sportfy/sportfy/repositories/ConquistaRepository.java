package com.sportfy.sportfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.sportfy.sportfy.models.Conquista;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {
    List<Conquista> findByMetaEsportivaIdMetaEsportivaAndAtivo(Long idMetaEsportiva, Boolean ativo);
    List<Conquista> findByAcademicoIdAcademicoAndAtivo(Long idAcademico, Boolean ativo);
    List<Conquista> findByAcademicoIdAcademicoAndConquistadoAndAtivo(Long idAcademico, Boolean conquistado, Boolean ativo);
    List<Conquista> findByAcademicoIdAcademicoAndMetaEsportivaModalidadeEsportivaIdModalidadeEsportiva(Long idAcademico, Long idModalidadeEsportiva);
    Conquista findByIdConquistaAndAcademicoIdAcademicoAndAtivo(Long idConquista, Long idAcademico, Boolean ativo);
    @Query("SELECT c FROM Conquista c WHERE c.academico.idAcademico = :idAcademico AND c.metaEsportiva.idMetaEsportiva IN :idMetaEsportivas AND c.conquistado = true")
    List<Conquista> findConqueredByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportivaIn(Long idAcademico, List<Long> idMetaEsportivas);    
}
