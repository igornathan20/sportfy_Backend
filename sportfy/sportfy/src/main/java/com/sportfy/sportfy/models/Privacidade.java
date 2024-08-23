package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoPrivacidade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="privacidade")
@NoArgsConstructor
@AllArgsConstructor
public class Privacidade implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_privacidade")
    @Setter @Getter
    private Long idPrivacidade;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_privacidade", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoPrivacidade tipoPrivacidade;
}
