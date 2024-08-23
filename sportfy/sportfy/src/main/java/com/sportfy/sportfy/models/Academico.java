// PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR // // PARTE DO IGOR //
package com.sportfy.sportfy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "academico")
public class Academico extends Usuario{
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
// @Table(name="academico")
// @NoArgsConstructor
// @AllArgsConstructor
// public class Academico implements Serializable {
//     @Id
//     @GeneratedValue(strategy=GenerationType.IDENTITY)
//     @Column(name="id_academico")
//     @Setter @Getter
//     private Long idAcademico;

//     @Column(name="curso")
//     @Setter @Getter
//     private String curso;

//     @ManyToOne(fetch=FetchType.EAGER)
//     @JoinColumn(name="id_usuario", updatable = false, nullable = false)
//     @Setter @Getter
//     private Usuario usuario;
// }
// PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS // // PARTE DO MATHEUS //