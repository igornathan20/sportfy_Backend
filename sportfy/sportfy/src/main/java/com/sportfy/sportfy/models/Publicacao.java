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
@Setter @Getter
public class Publicacao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_publicacao")
    private Long idPublicacao;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_publicacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataPublicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_canal", updatable = false, nullable = false)
    private Canal canal;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    private Usuario usuario;

    @Column(name="id_modalidade_esportiva")
    private Long idModalidadeEsportiva;

    @OneToMany(mappedBy="publicacao", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    private List<CurtidaPublicacao> listaCurtidaPublicacao;

    @OneToMany(mappedBy="publicacao", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    private List<Comentario> listaComentario;

    public void cadastrar(PublicacaoDto publicacaoDto) {
        this.idPublicacao = 0L;
        this.titulo = publicacaoDto.titulo();
        this.descricao = publicacaoDto.descricao();
        this.canal = new Canal();
        this.canal.setIdCanal(publicacaoDto.idCanal());
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(publicacaoDto.Usuario().idUsuario());
        this.idModalidadeEsportiva = publicacaoDto.idModalidadeEsportiva();
    }
}
