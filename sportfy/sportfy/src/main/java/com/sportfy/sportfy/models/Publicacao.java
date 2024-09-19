package com.sportfy.sportfy.models;

import java.io.Serializable;

import org.hibernate.annotations.CreationTimestamp;

import com.sportfy.sportfy.dtos.PublicacaoDto;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="publicacao")
@NoArgsConstructor
@AllArgsConstructor
public class Publicacao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_publicacao")
    @Setter @Getter
    private Long idPublicacao;

    @Column(name="descricao", nullable = false)
    @Setter @Getter
    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_publicacao", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataPublicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_canal", updatable = false, nullable = false)
    @Setter @Getter
    private Canal canal;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    @Setter @Getter
    private Usuario usuario;

    @OneToMany(mappedBy="publicacao", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    @Setter @Getter
    private List<CurtidaPublicacao> listaCurtidaPublicacao;

    @OneToMany(mappedBy="publicacao", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    @Setter @Getter
    private List<Comentario> listaComentario;

    public void cadastrar(PublicacaoDto publicacaoDto) {
        this.idPublicacao = 0L;
        this.descricao = publicacaoDto.descricao();
        this.canal = new Canal();
        this.canal.setIdCanal(publicacaoDto.idCanal());
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(publicacaoDto.Usuario().idUsuario());
    }
}
