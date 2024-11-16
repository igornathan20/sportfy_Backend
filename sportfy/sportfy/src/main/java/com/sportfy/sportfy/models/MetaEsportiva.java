package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.MetaEsportivaDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="meta_esportiva")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetaEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_meta_esportiva")
    private Long idMetaEsportiva;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="foto")
    private String foto;

    @Column(name="ativo", insertable = false)
    private boolean ativo = true;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    public void fromDto(MetaEsportivaDto metaEsportivaDto) {
        if (metaEsportivaDto.titulo() != null) {
            this.titulo = metaEsportivaDto.titulo();
        }
        if (metaEsportivaDto.descricao() != null) {
            this.descricao = metaEsportivaDto.descricao();
        }
        if (metaEsportivaDto.foto() != null) {
            this.foto = metaEsportivaDto.foto();
        }
    }
}
