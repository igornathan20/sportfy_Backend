package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Campeonato;
import com.sportfy.sportfy.models.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
    Optional<Time> findByNomeAndCampeonato(String nome, Campeonato campeonato);
    List<Time> findByCampeonato(Campeonato campeonato);
}
