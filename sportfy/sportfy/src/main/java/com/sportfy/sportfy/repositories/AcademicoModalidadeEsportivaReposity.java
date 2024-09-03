package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.AcademicoModalidadeEsportiva;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicoModalidadeEsportivaReposity extends JpaRepository<AcademicoModalidadeEsportiva, Long> {
    Optional<AcademicoModalidadeEsportiva> findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(Long academicoId, Long modalidadeEsportivaId);

}
