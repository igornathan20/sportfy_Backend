package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.AcademicoModalidadeEsportiva;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AcademicoModalidadeEsportivaRepository extends JpaRepository<AcademicoModalidadeEsportiva, Long> {
    Optional<AcademicoModalidadeEsportiva> findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(Long academicoId, Long modalidadeEsportivaId);
    List<AcademicoModalidadeEsportiva> findByAcademico(Academico academico);
    List<AcademicoModalidadeEsportiva> findByModalidadeEsportiva(ModalidadeEsportiva modalidadeEsportiva);
    List<AcademicoModalidadeEsportiva> findByAcademicoIdAcademicoAndModalidadeEsportivaAtivo(Long academicoId, boolean ativo);
}
