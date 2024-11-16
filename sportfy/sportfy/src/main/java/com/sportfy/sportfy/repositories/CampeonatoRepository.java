package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Campeonato;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.enums.TipoSituacao;
import com.sportfy.sportfy.models.Academico;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>, JpaSpecificationExecutor<Campeonato> {
    boolean existsByCodigo(String codigo);

    List<Campeonato> findByModalidadeEsportiva(ModalidadeEsportiva modalidadeEsportiva);

    Page<Campeonato> findByAcademico(Academico academico, Pageable pageable);

    List<Campeonato> findByAcademicoIdAcademico(Long idAcademico);

    List<Campeonato> findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(Long idAcademico, Long idModalidadeEsportiva);

    Optional<List<Campeonato>> findByTituloAndAtivo(String titulo, boolean ativo);

    @Query("SELECT c FROM Campeonato c WHERE c.situacaoCampeonato = :situacao AND c.dataCriacao BETWEEN :dataInicio AND CURRENT_TIMESTAMP")
    List<Campeonato> findBySituacaoAndDataCriacaoInLast30Days(@Param("situacao") TipoSituacao situacao, @Param("dataInicio") OffsetDateTime dataInicio);

    Page<Campeonato> findAll(Pageable pageable);
}
