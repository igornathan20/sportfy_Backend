package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    List<Jogador> findByAcademico(Academico academico);
    List<Jogador> findByAcademicoIdAcademico(Long idAcademico);
    List<Jogador> findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(Long idAcademico, Long idModalidadeEsportiva);
    Optional<Jogador> findByAcademicoAndTimeCampeonato( Academico academico, Campeonato campeonato);
    List<Jogador> findByAcademicoAndTimeCampeonatoModalidadeEsportiva( Academico academico, ModalidadeEsportiva modalidadeEsportiva);
    List<Jogador> findByTimeCampeonato( Campeonato campeonato);
    List<Jogador> findByTime( Time time);
}
