package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.repositories.ConquistaRepository;
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;
import org.hibernate.annotations.CreationTimestamp;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="modalidade_esportiva")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_modalidade_esportiva")
    private Long idModalidadeEsportiva;

    @Column(name="nome", unique = true)
    private String nome;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="foto")
    private String foto;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name="ativo", insertable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy="modalidadeEsportiva", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<MetaEsportiva> listaMetaEsportiva;

    public void inativar(MetaEsportivaRepository metaEsportivaRepository, ConquistaRepository conquistaRepository) {
        // Inativar a modalidade esportiva
        this.setAtivo(false);
        // Inativar todas as metas associadas a essa modalidade
        List<MetaEsportiva> metasAtuais = metaEsportivaRepository.findByModalidadeEsportivaIdModalidadeEsportiva(this.idModalidadeEsportiva);
        for (MetaEsportiva meta : metasAtuais) {
            meta.setAtivo(false);
            // Inativar todas as conquistas associadas a essa meta esportiva
            List<Conquista> conquistasAtuais = conquistaRepository.findByMetaEsportivaIdMetaEsportivaAndAtivo(meta.getIdMetaEsportiva(), true);
            for (Conquista conquista : conquistasAtuais) {
                conquista.setAtivo(false);
            }
            conquistaRepository.saveAll(conquistasAtuais);
        }
        metaEsportivaRepository.saveAll(metasAtuais);
    }

    public void fromDto(ModalidadeEsportivaDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("O DTO não pode ser nulo.");
        }

        if (dto.nome() != null) {
            this.setNome(dto.nome());
        }

        if (dto.descricao() != null) {
            this.setDescricao(dto.descricao());
        }

        if (dto.foto() != null) {
            this.setFoto(dto.foto());
        }
    }
}
