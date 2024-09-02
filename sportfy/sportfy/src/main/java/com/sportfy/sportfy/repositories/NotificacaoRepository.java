package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    Notificacao findByIdAcademico(Long idAcademico);
}
