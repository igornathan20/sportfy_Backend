package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="modalidade_esportiva")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_modalidade_esportiva")
    private Long idModalidadeEsportiva;

    @Column(name="nome", nullable = false)
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
    @Getter
    private boolean ativo;

    public ModalidadeEsportiva toEntity(ModalidadeEsportivaDto dto) {
        ModalidadeEsportiva modalidadeEsportiva = new ModalidadeEsportiva();
        modalidadeEsportiva.setNome(dto.nome());
        modalidadeEsportiva.setDescricao(dto.descricao());
        modalidadeEsportiva.setFoto(dto.foto());
        modalidadeEsportiva.setAtivo(dto.ativo());
        return modalidadeEsportiva;
    }
}
