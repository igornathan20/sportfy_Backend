package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoSituacaoMetaDiaria;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="situacao_meta_diaria")
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoMetaDiaria implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_situacao_meta_diaria")
    @Setter @Getter
    private Long idSituacaoMetaDiaria;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_situacao_meta_diaria", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoSituacaoMetaDiaria tipoSituacaoMetaDiaria;
}