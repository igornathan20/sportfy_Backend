package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Avaliacao;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvaliacaoJogadorRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByAcademicoAvaliadoAndModalidadeEsportiva(Academico academico, ModalidadeEsportiva modalidadeEsportiva);
    List<Avaliacao> findByAcademicoAvaliado(Academico academico);
}
