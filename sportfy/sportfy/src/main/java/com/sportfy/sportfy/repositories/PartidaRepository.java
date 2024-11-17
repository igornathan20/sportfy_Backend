package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.enums.TipoFasePartida;
import com.sportfy.sportfy.models.Campeonato;
import com.sportfy.sportfy.models.Partida;
import com.sportfy.sportfy.models.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByCampeonatoAndFasePartida(Campeonato campeonato, TipoFasePartida fasePartida);
    List<Partida> findByTime1OrTime2(Time time1, Time time2);
    List<Partida> findByCampeonato(Campeonato campeonato);
}
