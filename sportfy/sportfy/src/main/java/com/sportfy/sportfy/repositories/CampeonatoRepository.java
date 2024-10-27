package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Campeonato;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.Academico;


import java.util.List;
import java.util.Optional;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>, JpaSpecificationExecutor<Campeonato> {
    boolean existsByCodigo(String codigo);
    List<Campeonato> findByModalidadeEsportiva(ModalidadeEsportiva modalidadeEsportiva);
    List<Campeonato> findByAcademico(Academico academico);

    Optional<List<Campeonato>> findByTituloAndAtivo(String titulo, boolean ativo);

}
