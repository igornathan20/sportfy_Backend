package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.AdministradorDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrador")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Administrador implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_administrador")
    private Long idAdministrador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public void cadastrar(AdministradorDto administradorDto) {
        this.idAdministrador = 0L;
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(0L);
        this.usuario.setUsername(administradorDto.username().toLowerCase());
        this.usuario.setNome(administradorDto.nome());
        this.usuario.setTelefone(administradorDto.telefone());
        this.usuario.setDataNascimento(administradorDto.dataNascimento());
        this.usuario.setFoto(administradorDto.foto());
    }

    public void atualizar(Long idAdministrador, Long idUsuario, AdministradorDto administradorDto) {
        this.idAdministrador = idAdministrador;
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
        this.usuario.setIdUsuario(idUsuario);
        if (administradorDto.username() != null) {
            this.usuario.setUsername(administradorDto.username().toLowerCase());
        }
        if (administradorDto.nome() != null) {
            this.usuario.setNome(administradorDto.nome());
        }
        if (administradorDto.telefone() != null) {
            this.usuario.setTelefone(administradorDto.telefone());
        }
        if (administradorDto.dataNascimento() != null) {
            this.usuario.setDataNascimento(administradorDto.dataNascimento());
        }
        if (administradorDto.foto() != null) {
            this.usuario.setFoto(administradorDto.foto());
        }
    }
}
