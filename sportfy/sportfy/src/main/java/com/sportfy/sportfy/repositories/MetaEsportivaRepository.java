package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.MetaEsportiva;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface MetaEsportivaRepository extends JpaRepository<MetaEsportiva, Long> {
    List<MetaEsportiva> findByModalidadeEsportivaIdModalidadeEsportiva(Long idModalidadeEsportiva);
    List<MetaEsportiva> findByModalidadeEsportivaIdModalidadeEsportivaAndAtivo(Long idModalidadeEsportiva, boolean ativo);
    Optional<MetaEsportiva> findByIdMetaEsportivaAndAtivo(Long idMetaEsportiva, boolean ativo);
    Optional<MetaEsportiva> findByTituloAndAtivo(String titulo, boolean ativo);
    List<MetaEsportiva> findByModalidadeEsportivaAndAtivo(ModalidadeEsportiva modalidadeEsportiva, boolean ativo);
}
