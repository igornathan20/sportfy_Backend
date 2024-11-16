package com.sportfy.sportfy.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="usuario_canal")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class UsuarioCanal implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario_canal")
    private Long idUsuarioCanal;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_canal", updatable = false, nullable = false)
    private Canal canal;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    private Usuario usuario;
}
