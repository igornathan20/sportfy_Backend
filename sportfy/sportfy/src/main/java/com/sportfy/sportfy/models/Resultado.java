package com.sportfy.sportfy.models;

import com.sportfy.sportfy.dtos.ResultadoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name="resultado")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_resultado")
    private Long id;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time")
    private Time vencedor;

    @Column(name="pontuacao_time1")
    private int pontuacaoTime1;

    @Column(name="pontuacao_time2")
    private int pontuacaoTime2;

    @Column(name="descricao")
    private String descricao;

    public ResultadoDto toDto(Resultado resultado) {
        return new ResultadoDto(
                resultado != null ? resultado.getId() : null,
                resultado != null && resultado.getVencedor() != null ? resultado.getVencedor().getIdTime() : null,
                resultado != null ? resultado.getPontuacaoTime1() : 0,
                resultado != null ? resultado.getPontuacaoTime2() : 0,
                resultado != null ? resultado.getDescricao() : null
        );
    }

}