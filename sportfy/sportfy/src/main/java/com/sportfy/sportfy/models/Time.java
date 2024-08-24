package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="time")
@NoArgsConstructor
@AllArgsConstructor
public class Time implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_time")
    @Setter @Getter
    private Long idTime;

    @Column(name="nome", nullable = false)
    @Setter @Getter
    private String nome;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato", updatable = false, nullable = false)
    @Setter @Getter
    private Campeonato campeonato;
}
