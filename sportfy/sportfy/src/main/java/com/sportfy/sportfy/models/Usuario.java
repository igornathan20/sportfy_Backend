package com.sportfy.sportfy.models;

import java.util.Collection;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario")
    @Setter @Getter
    private Long idUsuario;

    @Column(name="username", unique = true)
    @Setter @Getter
    private String username;

    @Column(name="email", unique = true)
    @Setter @Getter
    private String email;

    @Column(name="password", nullable = false)
    @Setter @Getter
    private String password;

    @Column(name="nome", nullable = false)
    @Setter @Getter
    private String nome;

    @Column(name="cpf", unique = true)
    @Setter @Getter
    private String cpf;

    @Column(name="telefone")
    @Setter @Getter
    private String telefone;

    @Temporal(TemporalType.DATE)
    @Column(name="data_nascimento", nullable = false)
    @Setter @Getter
    private LocalDate dataNascimento;

    @Column(name="foto")
    @Setter @Getter
    private String foto;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    @Setter @Getter
    private OffsetDateTime dataCriacao;

    @Column(name="ativo", insertable = false)
    @Setter @Getter
    private boolean ativo;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_permissao")
    @Setter @Getter
    private Permissao permissao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
