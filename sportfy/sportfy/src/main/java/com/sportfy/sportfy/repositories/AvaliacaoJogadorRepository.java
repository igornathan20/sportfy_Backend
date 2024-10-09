package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.AvaliacaoJogador;
import com.sportfy.sportfy.models.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoJogadorRepository extends JpaRepository<AvaliacaoJogador, Long> {
    List<AvaliacaoJogador> findByJogador(Jogador jogador);
}
