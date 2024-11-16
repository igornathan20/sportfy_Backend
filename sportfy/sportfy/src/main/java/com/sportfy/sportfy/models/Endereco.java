package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.EnderecoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="endereco")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_endereco")
    private Long idEndereco;

    @Column(name="cep", nullable = false)
    private String cep;

    @Column(name="uf", nullable = false)
    private String uf;

    @Column(name="cidade", nullable = false)
    private String cidade;

    @Column(name="bairro", nullable = false)
    private String bairro;

    @Column(name="rua", nullable = false)
    private String rua;

    @Column(name="numero", nullable = false)
    private int numero;

    @Column(name="complemento")
    private String complemento;

    public void toEntity(EnderecoDto enderecoDto) {
        if (enderecoDto.cep() != null) {
            setCep(enderecoDto.cep());
        }
        if (enderecoDto.uf() != null) {
            setUf(enderecoDto.uf());
        }
        if (enderecoDto.cidade() != null) {
            setCidade(enderecoDto.cidade());
        }
        if (enderecoDto.bairro() != null) {
            setBairro(enderecoDto.bairro());
        }
        if (enderecoDto.rua() != null) {
            setRua(enderecoDto.rua());
        }

        setNumero(enderecoDto.numero());

        if (enderecoDto.complemento() != null) {
           setComplemento(enderecoDto.complemento());
        }
    }
}
