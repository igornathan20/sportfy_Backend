package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.enums.TipoNotificacao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="notificacao")
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_notificacao")
    @Setter @Getter
    private Long idNotificacao;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_notificacao", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoNotificacao tipoNotificacao;
}
