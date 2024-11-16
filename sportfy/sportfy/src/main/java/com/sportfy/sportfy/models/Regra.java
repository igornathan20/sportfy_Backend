package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.RegraDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="regra")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Regra implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_regra")
    private Long idRegra;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    public void cadastrar(RegraDto regraDto) {
        this.titulo = regraDto.titulo();
        this.descricao = regraDto.descricao();
    }
}
