package com.sportfy.sportfy.repositories;

import com.sportfy.sportfy.models.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador,Long> {
    Administrador findByUsername(String username);
}
