package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.dtos.AcademicoDto;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "academico")
@NoArgsConstructor
@AllArgsConstructor
public class Academico implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_academico")
    @Setter @Getter
    private Long idAcademico;

    @Column(name="email", unique = true)
    @Setter @Getter
    private String email;

    @Column(name="curso")
    @Setter @Getter
    private String curso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", nullable = false)
    @Setter @Getter
    private Usuario usuario;

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

    public void atualizar(Long idAcademico, Long idUsuario, AcademicoDto academicoDto) {
        this.idAcademico = idAcademico;
        this.email = academicoDto.email().toLowerCase();
        this.curso = academicoDto.curso();
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(idUsuario);
        this.usuario.setUsername(academicoDto.username().toLowerCase());
        this.usuario.setNome(academicoDto.nome());
        this.usuario.setGenero(academicoDto.genero());
        this.usuario.setTelefone(academicoDto.telefone());
        this.usuario.setDataNascimento(academicoDto.dataNascimento());
        this.usuario.setFoto(academicoDto.foto());
    }

}
