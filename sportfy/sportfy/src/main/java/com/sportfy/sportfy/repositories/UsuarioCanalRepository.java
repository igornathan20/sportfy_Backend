package com.sportfy.sportfy.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sportfy.sportfy.models.UsuarioCanal;

@Repository
public interface UsuarioCanalRepository extends JpaRepository<UsuarioCanal, Long> {
    List<UsuarioCanal> findByUsuarioIdUsuario(Long idUsuario);
}
