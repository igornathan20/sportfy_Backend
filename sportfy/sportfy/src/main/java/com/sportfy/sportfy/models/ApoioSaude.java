package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.dtos.ApoioSaudeDto;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="apoio_saude")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApoioSaude implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_apoio_saude")
    private Long idApoioSaude;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="email")
    private String email;

    @Column(name="telefone")
    private String telefone;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_publicacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataPublicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_administrador", updatable = false, nullable = false)
    private Administrador administrador;

    public void updateFromDto(ApoioSaudeDto dto) {
        this.nome =(dto.nome());
        this.email = (dto.email());
        this.telefone = (dto.telefone());
        this.descricao = (dto.descricao());
    }
}
