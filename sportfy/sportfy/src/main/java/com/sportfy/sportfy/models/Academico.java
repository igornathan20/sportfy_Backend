package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.List;
import com.sportfy.sportfy.dtos.AcademicoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "academico")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Academico implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico")
    private Long idAcademico;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="curso")
    private String curso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "academico_modalidade_esportiva",
            joinColumns = @JoinColumn(name = "id_academico"),
            inverseJoinColumns = @JoinColumn(name = "id_modalidade_esportiva")
    )
    private List<ModalidadeEsportiva> modalidadeEsportivas;

    public void toEntity(AcademicoDto academicoDto) {
        this.idAcademico = 0L;
        this.email = academicoDto.email().toLowerCase();
        this.curso = academicoDto.curso().toLowerCase();
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(0L);
        this.usuario.setUsername(academicoDto.username().toLowerCase());
        this.usuario.setNome(academicoDto.nome());
        this.usuario.setGenero(academicoDto.genero());
        this.usuario.setTelefone(academicoDto.telefone());
        this.usuario.setDataNascimento(academicoDto.dataNascimento());
        this.usuario.setFoto(academicoDto.foto());
    }

    public static AcademicoDto toDto(Academico academico) {
        return new AcademicoDto(
                academico.getIdAcademico(),
                academico.getCurso(),
                academico.getUsuario().getUsername(),
                academico.getEmail(),
                null,
                academico.getUsuario().getNome(),
                academico.getUsuario().getGenero(),
                academico.getUsuario().getTelefone(),
                academico.getUsuario().getDataNascimento(),
                academico.getUsuario().getFoto(),
                academico.getUsuario().getDataCriacao(),
                academico.getUsuario().isAtivo(),
                academico.getUsuario().getPermissao()
        );
    }

    public void atualizar(Long idAcademico, Long idUsuario, AcademicoDto academicoDto) {
        if (idAcademico != null) {
            this.idAcademico = idAcademico;
        }
        if (academicoDto.email() != null) {
            this.email = academicoDto.email().toLowerCase();
        }
        if (academicoDto.curso() != null) {
            this.curso = academicoDto.curso();
        }
        if (idUsuario != null) {
            if (this.usuario == null) {
                this.usuario = new Usuario();
            }
            this.usuario.setIdUsuario(idUsuario);
        }
        if (academicoDto.username() != null) {
            this.usuario.setUsername(academicoDto.username().toLowerCase());
        }
        if (academicoDto.nome() != null) {
            this.usuario.setNome(academicoDto.nome());
        }
        if (academicoDto.genero() != null) {
            this.usuario.setGenero(academicoDto.genero());
        }
        if (academicoDto.telefone() != null) {
            this.usuario.setTelefone(academicoDto.telefone());
        }
        if (academicoDto.dataNascimento() != null) {
            this.usuario.setDataNascimento(academicoDto.dataNascimento());
        }
        if (academicoDto.foto() != null) {
            this.usuario.setFoto(academicoDto.foto());
        }
    }
}
