package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
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
    private Long idConquista;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_conquista", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataConquista;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_meta_esportiva", updatable = false, nullable = false)
    private MetaEsportiva metaEsportiva;

    public void cadastrar(Long idAcademico, Long idMetaEsportiva) {
        this.idConquista = 0L;
        this.academico = new Academico();
        academico.setIdAcademico(idAcademico);
        this.metaEsportiva = new MetaEsportiva();
        metaEsportiva.setIdMetaEsportiva(idMetaEsportiva);
    }
}
