package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.dtos.AdministradorDto;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "administrador")
@NoArgsConstructor
@AllArgsConstructor
public class Administrador implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_administrador")
    @Setter @Getter
    private Long idAdministrador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", nullable = false)
    @Setter @Getter
    private Usuario usuario;

    public void cadastrar(AdministradorDto administradorDto) {
        this.idAdministrador = 0L;
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(0L);
        this.usuario.setUsername(administradorDto.username().toLowerCase());
        this.usuario.setEmail(administradorDto.email().toLowerCase());
        this.usuario.setNome(administradorDto.nome());
        this.usuario.setCpf(administradorDto.cpf());
        this.usuario.setTelefone(administradorDto.telefone());
        this.usuario.setDataNascimento(administradorDto.dataNascimento());
        this.usuario.setFoto(administradorDto.foto());
    }

    public void atualizar(Long idAdministrador, Long idUsuario, AdministradorDto administradorDto) {
        this.idAdministrador = idAdministrador;
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(idUsuario);
        this.usuario.setUsername(administradorDto.username().toLowerCase());
        this.usuario.setEmail(administradorDto.email().toLowerCase());
        this.usuario.setNome(administradorDto.nome());
        this.usuario.setCpf(administradorDto.cpf());
        this.usuario.setTelefone(administradorDto.telefone());
        this.usuario.setDataNascimento(administradorDto.dataNascimento());
        this.usuario.setFoto(administradorDto.foto());
    }

}
