package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.MetaDiariaDto;
import com.sportfy.sportfy.enums.TipoSituacaoMetaDiaria;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="meta_diaria")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetaDiaria implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_meta_diaria")
    private Long idMetaDiaria;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="objetivo", nullable = false)
    private String objetivo;

    @Column(name="progresso_atual", insertable = false)
    private int progressoAtual;

    @Column(name="progresso_maximo", nullable = false)
    private int progressoMaximo;

    @Column(name="progresso_item", nullable = false)
    private String progressoItem;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_situacao_meta_diaria", nullable = false)
    private TipoSituacaoMetaDiaria tipoSituacaoMetaDiaria;

    public void updateFromDto(MetaDiariaDto dto) {
        this.titulo = dto.titulo();
        this.objetivo = dto.objetivo();
        this.progressoAtual = dto.progressoAtual();
        this.progressoMaximo = dto.progressoMaximo();
        this.progressoItem = dto.progressoItem();

        switch (dto.situacaoMetaDiaria()){
            case 0:
                this.tipoSituacaoMetaDiaria = TipoSituacaoMetaDiaria.EM_ANDAMENTO;
                break;
            case 1:
                this.tipoSituacaoMetaDiaria = TipoSituacaoMetaDiaria.CONCLUIDA;
                break;
            default:
                this.tipoSituacaoMetaDiaria = TipoSituacaoMetaDiaria.EM_ANDAMENTO;
        }
    }

    public static MetaDiariaDto toDto(MetaDiaria metaDiaria) {
        return new MetaDiariaDto(
                metaDiaria.getIdMetaDiaria(),
                metaDiaria.getTitulo(),
                metaDiaria.getObjetivo(),
                metaDiaria.getProgressoAtual(),
                metaDiaria.getProgressoMaximo(),
                metaDiaria.getProgressoItem(),
                metaDiaria.getAcademico() != null ? metaDiaria.getAcademico().getIdAcademico() : null,
                metaDiaria.getTipoSituacaoMetaDiaria().ordinal()
        );
    }
}
