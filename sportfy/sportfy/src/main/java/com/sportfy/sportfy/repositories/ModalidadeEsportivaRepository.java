package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Administrador;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModalidadeEsportivaRepository extends JpaRepository<ModalidadeEsportiva, Long> {
    Optional<ModalidadeEsportiva> findByNome(String nome);

}
