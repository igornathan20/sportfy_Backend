package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;

import com.sportfy.sportfy.dtos.ConquistaDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="conquista")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Conquista implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_conquista")
    private Long idConquista = 0L;

    @Column(name="progresso_atual", insertable = false, nullable = false)
    private int progressoAtual = 0;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_conquista", insertable = false, nullable = true)
    private OffsetDateTime dataConquista;

    @Column(name="conquistado", insertable = false, nullable = false)
    private boolean conquistado = false;

    @Column(name="ativo", insertable = false, nullable = false)
    private boolean ativo = true;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_meta_esportiva", updatable = false, nullable = false)
    private MetaEsportiva metaEsportiva;

    public void cadastrar(Long idAcademico, Long idMetaEsportiva) {
        this.academico = new Academico();
        academico.setIdAcademico(idAcademico);
        this.metaEsportiva = new MetaEsportiva();
        metaEsportiva.setIdMetaEsportiva(idMetaEsportiva);
    }

    public void atualizar(ConquistaDto conquistaDto) {
        if (conquistaDto.dataConquista() == null) {
            if (conquistaDto.progressoAtual() >= conquistaDto.metaEsportiva().progressoMaximo()) {
                this.progressoAtual = conquistaDto.metaEsportiva().progressoMaximo();
                this.dataConquista = OffsetDateTime.now();
                this.conquistado = true;
            } else {
                this.progressoAtual = conquistaDto.progressoAtual();
                this.dataConquista = null;
                this.conquistado = false;
            }
        } else {
            this.progressoAtual = conquistaDto.progressoAtual();
            this.dataConquista = conquistaDto.dataConquista();
            this.conquistado = conquistaDto.conquistado();
        }
        this.idConquista = conquistaDto.idConquista();
        this.academico = new Academico();
        academico.setIdAcademico(conquistaDto.idAcademico());
        this.metaEsportiva = new MetaEsportiva();
        metaEsportiva.setIdMetaEsportiva(conquistaDto.metaEsportiva().idMetaEsportiva());
    }
}
