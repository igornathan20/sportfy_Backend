// PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR //
package com.sportfy.sportfy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Usuario implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    @NotBlank
    private String username;
    @Column(length = 50)
    @NotBlank
    private String email;
    private String password;
    @Column(length = 50)
    @NotBlank
    private String nome;
    @Column(length = 11)
    @NotBlank
    private String cpf;
    @Column(length = 12)
    private String telefone;
    private LocalDate dataCriacao;
    @Enumerated(EnumType.STRING)
    private Roles userRole;

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
}
// PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR //
// PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS //
// package com.sportfy.sportfy.models;

// import java.io.Serializable;
// import jakarta.persistence.*;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// import java.time.LocalDate;
// import java.time.OffsetDateTime;

// import org.hibernate.annotations.CreationTimestamp;

// @Entity
// @Table(name="usuario")
// @NoArgsConstructor
// @AllArgsConstructor
// public class Usuario implements Serializable {
//     @Id
//     @GeneratedValue(strategy=GenerationType.IDENTITY)
//     @Column(name="id_usuario")
//     @Setter @Getter
//     private Long idUsuario;

//     @Column(name="nome_usuario", unique = true)
//     @Setter @Getter
//     private String nomeUsuario;

//     @Column(name="email", unique = true)
//     @Setter @Getter
//     private String email;

//     @Column(name="senha", nullable = false)
//     @Setter @Getter
//     private String senha;

//     @Column(name="nome", nullable = false)
//     @Setter @Getter
//     private String nome;

//     @Column(name="cpf", unique = true)
//     @Setter @Getter
//     private String cpf;

//     @Column(name="telefone")
//     @Setter @Getter
//     private String telefone;

//     @Temporal(TemporalType.DATE)
//     @Column(name="data_nascimento", nullable = false)
//     @Setter @Getter
//     private LocalDate dataNascimento;

//     @Column(name="foto")
//     @Setter @Getter
//     private String foto;

//     @CreationTimestamp
//     @Temporal(TemporalType.TIMESTAMP)
//     @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
//     @Setter @Getter
//     private OffsetDateTime dataCriacao;

//     @Column(name="ativo", insertable = false)
//     @Setter @Getter
//     private boolean ativo;

//     @ManyToOne(fetch=FetchType.EAGER)
//     @JoinColumn(name="id_permissao")
//     @Setter @Getter
//     private Permissao permissao;
// }
// PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS //