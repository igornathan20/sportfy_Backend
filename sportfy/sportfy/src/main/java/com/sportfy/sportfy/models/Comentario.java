package com.sportfy.sportfy.models;

import java.io.Serializable;

import org.hibernate.annotations.CreationTimestamp;

import com.sportfy.sportfy.dtos.ComentarioDto;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="comentario")
@NoArgsConstructor
@AllArgsConstructor
public class Comentario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_comentario")
    @Setter @Getter
    private Long idComentario;

    @Column(name="descricao", nullable = false)
    @Setter @Getter
    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_comentario", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataComentario;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_publicacao", updatable = false, nullable = false)
    @Setter @Getter
    private Publicacao publicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    @Setter @Getter
    private Usuario usuario;

    @OneToMany(mappedBy="comentario", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    @Setter @Getter
    private List<CurtidaComentario> listaCurtidaComentario;

    public void cadastrar(ComentarioDto comentarioDto) {
        this.idComentario = 0L;
        this.descricao = comentarioDto.descricao();
        this.publicacao = new Publicacao();
        this.publicacao.setIdPublicacao(comentarioDto.idPublicacao());
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(comentarioDto.Usuario().idUsuario());
    }
}
