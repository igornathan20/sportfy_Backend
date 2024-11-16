package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.util.List;
import com.sportfy.sportfy.enums.TipoCanal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="canal")
@NoArgsConstructor
@AllArgsConstructor
public class Canal implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_canal")
    @Setter @Getter
    private Long idCanal;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_canal", insertable = false, updatable = false, nullable = false, unique = true)
    @Setter @Getter
    private TipoCanal tipoCanal;

    @OneToMany(mappedBy="canal", fetch=FetchType.EAGER)
    @Setter @Getter
    private List<UsuarioCanal> listaUsuarioCanal;

    @OneToMany(mappedBy="canal", fetch=FetchType.EAGER)
    @Setter @Getter
    private List<Publicacao> listaPublicacao;
}
