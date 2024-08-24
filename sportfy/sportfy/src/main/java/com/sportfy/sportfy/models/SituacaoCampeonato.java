package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoSituacaoCampeonato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="situacao_campeonato")
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoCampeonato implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_situacao_campeonato")
    @Setter @Getter
    private Long idSituacaoCampeonato;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_situacao_campeonato", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoSituacaoCampeonato tipoSituacaoCampeonato;
}
