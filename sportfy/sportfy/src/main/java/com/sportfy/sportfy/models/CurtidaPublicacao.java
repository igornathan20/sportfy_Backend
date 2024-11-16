package com.sportfy.sportfy.models;

import java.io.Serializable;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="curtida_publicacao")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class CurtidaPublicacao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_curtida_publicacao")
    private Long idCurtidaPublicacao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_curtida", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCurtida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_publicacao", updatable = false, nullable = false)
    private Publicacao publicacao;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    private Usuario usuario;

    public void cadastrar(Long idUsuario, Long idPublicacao) {
        this.idCurtidaPublicacao = 0L;
        this.publicacao = new Publicacao();
        this.publicacao.setIdPublicacao(idPublicacao);
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(idUsuario);
    }
}
