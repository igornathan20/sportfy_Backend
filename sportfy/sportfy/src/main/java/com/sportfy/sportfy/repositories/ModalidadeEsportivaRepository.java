package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public interface ModalidadeEsportivaRepository extends JpaRepository<ModalidadeEsportiva, Long> {
    Optional<ModalidadeEsportiva> findByNome(String nome);

    Optional<ModalidadeEsportiva> findByIdModalidadeEsportivaAndAtivo(Long idModalidadeEsportiva, boolean ativo);

    List<ModalidadeEsportiva> findByAtivo(boolean ativo);

    @Query("SELECT m FROM ModalidadeEsportiva m WHERE m.dataCriacao BETWEEN :dataInicio AND CURRENT_TIMESTAMP")
    List<ModalidadeEsportiva> findByDataCriacaoInLast30Days(@Param("dataInicio") OffsetDateTime dataInicio);

    @Query("SELECT DISTINCT m FROM ModalidadeEsportiva m LEFT JOIN FETCH m.listaMetaEsportiva metas WHERE m.ativo = true AND metas.ativo = true")
    List<ModalidadeEsportiva> findByAtivoTrueWithActiveMetas();
    
    @Query("SELECT m FROM ModalidadeEsportiva m LEFT JOIN FETCH m.listaMetaEsportiva metas WHERE m.nome = :nome AND m.ativo = true AND metas.ativo = true")
    Optional<ModalidadeEsportiva> findByNomeAndAtivoTrueWithActiveMetas(@Param("nome") String nome);

    List<ModalidadeEsportiva> findByNomeContainingAndAtivoTrue(String nome);
}
