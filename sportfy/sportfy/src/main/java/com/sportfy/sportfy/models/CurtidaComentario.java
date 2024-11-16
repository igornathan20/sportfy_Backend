package com.sportfy.sportfy.models;

import java.io.Serializable;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="curtida_comentario")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class CurtidaComentario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_curtida_comentario")
    private Long idCurtidaComentario;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_curtida", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCurtida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_comentario", updatable = false, nullable = false)
    private Comentario comentario;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", updatable = false, nullable = false)
    private Usuario usuario;

    public void cadastrar(Long idUsuario, Long idComentario) {
        this.idCurtidaComentario = 0L;
        this.comentario = new Comentario();
        this.comentario.setIdComentario(idComentario);
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(idUsuario);
    }
}
