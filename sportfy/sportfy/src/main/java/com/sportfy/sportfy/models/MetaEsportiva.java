package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="meta_esportiva")
@NoArgsConstructor
@AllArgsConstructor
public class MetaEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_meta_esportiva")
    @Setter @Getter
    private Long idMetaEsportiva;

    @Column(name="titulo", nullable = false)
    @Setter @Getter
    private String titulo;

    @Column(name="descricao", nullable = false)
    @Setter @Getter
    private String descricao;

    @Column(name="foto")
    @Setter @Getter
    private String foto;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    @Setter @Getter
    private ModalidadeEsportiva modalidadeEsportiva;
}
