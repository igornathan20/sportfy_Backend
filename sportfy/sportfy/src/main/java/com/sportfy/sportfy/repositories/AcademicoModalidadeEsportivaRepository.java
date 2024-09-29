package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.AcademicoModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface AcademicoModalidadeEsportivaRepository extends JpaRepository<AcademicoModalidadeEsportiva, Long> {
    Optional<AcademicoModalidadeEsportiva> findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(Long academicoId, Long modalidadeEsportivaId);
    List<AcademicoModalidadeEsportiva> findByAcademicoIdAcademicoAndModalidadeEsportivaAtivo(Long academicoId, boolean ativo);
}
