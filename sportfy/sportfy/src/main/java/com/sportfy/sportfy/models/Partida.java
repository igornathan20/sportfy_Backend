package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.PartidaDto;
import com.sportfy.sportfy.enums.TipoFasePartida;
import com.sportfy.sportfy.enums.TipoSituacao;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="partida")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Partida implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_partida")
    private Long idPartida;

    @Column(name="data_partida", nullable = false)
    private OffsetDateTime dataPartida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_campeonato", nullable = false)
    private Campeonato campeonato;

    @Enumerated(EnumType.STRING)
    @Column(name="fase_partida")
    private TipoFasePartida fasePartida;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time1")
    private Time time1;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_time2")
    private Time time2;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_partida", nullable = false)
    private TipoSituacao situacaoPartida = TipoSituacao.EM_ABERTO;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_resultado")
    private Resultado resultado = new Resultado();

    public PartidaDto toDto(Partida partida) {
        return new PartidaDto(
                partida != null ? partida.getIdPartida() : null,
                partida != null ? partida.getDataPartida() : null,
                partida != null && partida.getCampeonato() != null ? partida.getCampeonato().getIdCampeonato() : null,
                partida != null && partida.getFasePartida() != null ? partida.getFasePartida().name() : null,
                partida != null && partida.getTime1() != null ? partida.getTime1().getIdTime() : null,
                partida != null && partida.getTime2() != null ? partida.getTime2().getIdTime() : null,
                partida != null && partida.getSituacaoPartida() != null ? partida.getSituacaoPartida().name() : null,
                partida != null && partida.getResultado() != null ? partida.getResultado().toDto(partida.getResultado()) : null
        );
    }
}
