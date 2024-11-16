package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sportfy.sportfy.repositories.ConquistaRepository;
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="academico_modalidade_esportiva")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicoModalidadeEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico_modalidade_esportiva")
    private Long idAcademicoModalidadeEsportiva;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    public void inscrever(Long idAcademico, Long idModalidadeEsportiva, MetaEsportivaRepository metaEsportivaRepository, ConquistaRepository conquistaRepository) {
        List<MetaEsportiva> metas = metaEsportivaRepository.findByModalidadeEsportivaIdModalidadeEsportivaAndAtivo(idModalidadeEsportiva, true);
        List<Conquista> conquistas = new ArrayList<>();
        for (MetaEsportiva meta : metas) {
            Conquista conquista = new Conquista();
            conquista.setMetaEsportiva(meta);
            Academico academico = new Academico();
            academico.setIdAcademico(idAcademico);
            conquista.setAcademico(academico);
            conquistas.add(conquista);
        }
        conquistaRepository.saveAll(conquistas);
    }

    public void desinscrever(Long idAcademico, Long idModalidadeEsportiva, ConquistaRepository conquistaRepository) {
        List<Conquista> conquistas = conquistaRepository.findByAcademicoIdAcademicoAndMetaEsportivaModalidadeEsportivaIdModalidadeEsportiva(idAcademico, idModalidadeEsportiva);
        conquistaRepository.deleteAll(conquistas);
    }
}
