package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.MetaEsportiva;

import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface MetaEsportivaRepository extends JpaRepository<MetaEsportiva, Long> {
    List<MetaEsportiva> findByModalidadeEsportivaIdModalidadeEsportiva(Long idModalidadeEsportiva);
    Optional<MetaEsportiva> findByTitulo(String titulo);
    List<MetaEsportiva> findByModalidadeEsportivaAndAtivo(ModalidadeEsportiva modalidadeEsportiva, boolean ativo);
}
