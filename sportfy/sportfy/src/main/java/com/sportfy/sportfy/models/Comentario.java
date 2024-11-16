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
@Setter @Getter
public class Comentario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_comentario")
    private Long idComentario;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_comentario", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataComentario;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_publicacao", updatable = false, nullable = false)
    private Publicacao publicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy="comentario", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
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
