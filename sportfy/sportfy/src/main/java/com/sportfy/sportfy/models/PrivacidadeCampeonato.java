package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoPrivacidadeCampeonato;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="privacidade_campeonato")
@NoArgsConstructor
@AllArgsConstructor
public class PrivacidadeCampeonato implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_privacidade_campeonato")
    @Setter @Getter
    private Long idPrivacidadeCampeonato;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_privacidade_campeonato", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoPrivacidadeCampeonato tipoPrivacidadeCampeonato;
}
