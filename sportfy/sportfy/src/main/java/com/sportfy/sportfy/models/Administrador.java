package com.sportfy.sportfy.models;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "administrador")
// @NoArgsConstructor
// @AllArgsConstructor
public class Administrador extends Usuario {
    // @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    // @Column(name="id_administrador")
    // @Setter @Getter
    // private Long idAdministrador;
}
