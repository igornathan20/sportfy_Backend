// PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS //
package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails, Serializable {
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
}
// PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS //
// PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR //
// package com.sportfy.sportfy.models;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotBlank;
// import lombok.*;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import java.io.Serializable;
// import java.time.LocalDate;
// import java.util.Collection;

// @MappedSuperclass
// @AllArgsConstructor
// @NoArgsConstructor
// @EqualsAndHashCode(of = {"id"})
// @Getter
// @Setter
// public class Usuario implements UserDetails, Serializable {

//     private static final long serialVersionUID = 1L;
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @Column(length = 20)
//     @NotBlank
//     private String username;
//     @Column(length = 50)
//     @NotBlank
//     private String email;
//     private String password;
//     @Column(length = 50)
//     @NotBlank
//     private String nome;
//     @Column(length = 11)
//     @NotBlank
//     private String cpf;
//     @Column(length = 12)
//     private String telefone;
//     private LocalDate dataCriacao;
//     @Enumerated(EnumType.STRING)
//     private Roles userRole;

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return null;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return UserDetails.super.isAccountNonExpired();
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return UserDetails.super.isAccountNonLocked();
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return UserDetails.super.isCredentialsNonExpired();
//     }

//     @Override
//     public boolean isEnabled() {
//         return UserDetails.super.isEnabled();
//     }
// }
// PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR //
