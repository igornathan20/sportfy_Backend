package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.AvaliacaoJogador;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvaliacaoJogadorRepository extends JpaRepository<AvaliacaoJogador, Long> {
    List<AvaliacaoJogador> findByAcademicoAvaliadoAndModalidadeEsportiva(Academico academico, ModalidadeEsportiva modalidadeEsportiva);
    List<AvaliacaoJogador> findByAcademicoAvaliado(Academico academico);
}
