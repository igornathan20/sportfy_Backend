package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Campeonato;
import com.sportfy.sportfy.models.Jogador;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    List<Jogador> findByAcademico(Academico academico);
    Optional<Jogador> findByAcademicoAndTimeCampeonato( Academico academico, Campeonato Campeonato);
    List<Jogador> findByAcademicoAndTimeCampeonatoModalidadeEsportiva( Academico academico, ModalidadeEsportiva modalidadeEsportiva);

}
