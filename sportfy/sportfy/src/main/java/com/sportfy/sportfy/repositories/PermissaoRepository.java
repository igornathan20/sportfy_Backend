package com.sportfy.sportfy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sportfy.sportfy.enums.TipoPermissao;
import com.sportfy.sportfy.models.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    Optional<Permissao> findByTipoPermissao(TipoPermissao tipoPermissao);
}
