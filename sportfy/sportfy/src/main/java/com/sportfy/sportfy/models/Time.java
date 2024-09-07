package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="time")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Time implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_time")
    private Long idTime;

    @Column(name="nome", nullable = false)
    private String nome;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato", updatable = false, nullable = false)
    private Campeonato campeonato;
}
