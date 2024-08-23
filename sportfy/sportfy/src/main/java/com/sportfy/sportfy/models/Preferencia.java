package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoPreferencia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="preferencia")
@NoArgsConstructor
@AllArgsConstructor
public class Preferencia implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_preferencia")
    @Setter @Getter
    private Long idPreferencia;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_preferencia", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoPreferencia tipoPreferencia;
}
