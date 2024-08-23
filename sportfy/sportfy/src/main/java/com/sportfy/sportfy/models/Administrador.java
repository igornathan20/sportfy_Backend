// PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR //
package com.sportfy.sportfy.models;

import jakarta.persistence.*;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario{
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

// @Entity
// @Table(name="administrador")
// @NoArgsConstructor
// @AllArgsConstructor
// public class Administrador implements Serializable {
//     @Id
//     @GeneratedValue(strategy=GenerationType.IDENTITY)
//     @Column(name="id_administrador")
//     @Setter @Getter
//     private Long idAdministrador;

//     @ManyToOne(fetch=FetchType.EAGER)
//     @JoinColumn(name="id_usuario", updatable = false, nullable = false)
//     @Setter @Getter
//     private Usuario usuario;
// }
// PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS //