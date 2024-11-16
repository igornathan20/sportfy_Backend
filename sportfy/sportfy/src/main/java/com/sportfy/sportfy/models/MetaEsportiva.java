package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sportfy.sportfy.dtos.MetaEsportivaDto;
import com.sportfy.sportfy.repositories.AcademicoModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.ConquistaRepository;

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
    private Long idMetaEsportiva = 0L;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="progresso_maximo", nullable = false)
    private int progressoMaximo;

    @Column(name="progresso_item", nullable = false)
    private String progressoItem;

    @Column(name="foto", nullable = true)
    private String foto;

    @Column(name="ativo", insertable = false)
    private boolean ativo = true;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    public void cadastrarConquistas(AcademicoModalidadeEsportivaRepository academicoModalidadeEsportivaRepository, ConquistaRepository conquistaRepository) {
        List<AcademicoModalidadeEsportiva> academicoModalidadeEsportivas = academicoModalidadeEsportivaRepository.findByModalidadeEsportiva(this.modalidadeEsportiva);
        List<Conquista> conquistas = new ArrayList<>();
        for (AcademicoModalidadeEsportiva academicoModalidadeEsportiva : academicoModalidadeEsportivas) {
            Conquista conquista = new Conquista();
            conquista.setMetaEsportiva(this);
            conquista.setAcademico(academicoModalidadeEsportiva.getAcademico());
            conquistas.add(conquista);
        }
        conquistaRepository.saveAll(conquistas);
    }

    public void desativarConquistas(Long idMetaEsportiva, ConquistaRepository conquistaRepository) {
        this.ativo = false;
        List<Conquista> conquistas = conquistaRepository.findByMetaEsportivaIdMetaEsportivaAndAtivo(idMetaEsportiva, true);
        for (Conquista conquista : conquistas) {
            conquista.setAtivo(false);
        }
        conquistaRepository.saveAll(conquistas);
    }

    public void atualizar(MetaEsportivaDto metaEsportivaDto) {
        if (metaEsportivaDto.titulo() != null) {
            this.titulo = metaEsportivaDto.titulo();
        }
        if (metaEsportivaDto.descricao() != null) {
            this.descricao = metaEsportivaDto.descricao();
        }
        if (metaEsportivaDto.progressoItem() != null) {
            this.progressoItem = metaEsportivaDto.progressoItem();
        }
        if (metaEsportivaDto.foto() != null) {
            this.foto = metaEsportivaDto.foto();
        }
    }

    public void fromDto(MetaEsportivaDto metaEsportivaDto) {
        if (metaEsportivaDto.titulo() != null) {
            this.titulo = metaEsportivaDto.titulo();
        }
        if (metaEsportivaDto.descricao() != null) {
            this.descricao = metaEsportivaDto.descricao();
        }
        if (metaEsportivaDto.progressoMaximo() != null) {
            this.progressoMaximo = metaEsportivaDto.progressoMaximo();
        }
        if (metaEsportivaDto.progressoItem() != null) {
            this.progressoItem = metaEsportivaDto.progressoItem();
        }
        if (metaEsportivaDto.foto() != null) {
            this.foto = metaEsportivaDto.foto();
        }
        if (metaEsportivaDto.ativo() != null) {
            this.ativo = metaEsportivaDto.ativo();
        }
        if (metaEsportivaDto.idModalidadeEsportiva() != null) {
            this.modalidadeEsportiva = new ModalidadeEsportiva();
            this.modalidadeEsportiva.setIdModalidadeEsportiva(metaEsportivaDto.idModalidadeEsportiva());
        }
    }
}
