package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.enums.TipoPermissao;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="permissao")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Permissao implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_permissao")
    private Long idPermissao;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_permissao", insertable = false, updatable = false, nullable = false, unique = true)
    private TipoPermissao tipoPermissao;
}
