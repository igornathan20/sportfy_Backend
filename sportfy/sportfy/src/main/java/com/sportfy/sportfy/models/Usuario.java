package com.sportfy.sportfy.models;

import java.io.Serial;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.sportfy.sportfy.enums.TipoPermissao;

@Entity
@Table(name="usuario")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long idUsuario;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="genero")
    private String genero;

    @Column(name="telefone")
    private String telefone;

    @Column(name="data_nascimento", nullable = false)
    private OffsetDateTime dataNascimento;

    @Column(name="foto")
    private String foto;

    @CreationTimestamp
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name="ativo", insertable = false)
    private boolean ativo = true;

    @Column(name="permissao")
    private TipoPermissao permissao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + permissao.getTipoPermissao()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void inativar() {
        this.ativo = false;
    }

}
