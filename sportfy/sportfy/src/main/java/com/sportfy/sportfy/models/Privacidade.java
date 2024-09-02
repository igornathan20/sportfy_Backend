package com.sportfy.sportfy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="privacidade")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Privacidade implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_privacidade")
    private Long idPrivacidade;
    @Column(name="idAcademico", nullable = false, unique = true)
    private Long idAcademico;

    //@Enumerated(EnumType.STRING)
    //@Column(name="tipo_privacidade", insertable = false, updatable = false, nullable = false, unique = true)
    //private TipoPrivacidade tipoPrivacidade;

    @Column(name="mostrarModalidadesEsportivas")
    private boolean mostrarModalidadesEsportivas = true;
    @Column(name="mostrarHistoricoCampeonatos")
    private boolean mostrarHistoricoCampeonatos = true;
    @Column(name="mostrarEstatisticasModalidadesEsportivas")
    private boolean mostrarEstatisticasModalidadesEsportivas = true;
    @Column(name="mostrarConquistas")
    private boolean mostrarConquistas = true;
}
