package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoPermissao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="permissao")
@NoArgsConstructor
@AllArgsConstructor
public class Permissao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_permissao")
    @Setter @Getter
    private Long idPermissao;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_permissao", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoPermissao tipoPermissao;
}