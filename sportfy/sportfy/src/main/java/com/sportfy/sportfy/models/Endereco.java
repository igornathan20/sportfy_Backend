package com.sportfy.sportfy.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="endereco")
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_endereco")
    @Setter @Getter
    private Long idEndereco;

    @Column(name="cep", nullable = false)
    @Setter @Getter
    private String cep;

    @Column(name="uf", nullable = false)
    @Setter @Getter
    private String uf;

    @Column(name="cidade", nullable = false)
    @Setter @Getter
    private String cidade;

    @Column(name="bairro", nullable = false)
    @Setter @Getter
    private String bairro;

    @Column(name="rua", nullable = false)
    @Setter @Getter
    private String rua;

    @Column(name="numero", nullable = false)
    @Setter @Getter
    private String numero;

    @Column(name="complemento")
    @Setter @Getter
    private String complemento;
}
