package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.ApoioSaude;
import com.sportfy.sportfy.models.MetaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApoioSaudeRepository extends JpaRepository<ApoioSaude, Long> {
    List<ApoioSaude> findByNomeContainingIgnoreCase(String nome);

}
