package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.dtos.CampeonatoDto;
import com.sportfy.sportfy.enums.TipoPrivacidadeCampeonato;
import com.sportfy.sportfy.enums.TipoSituacaoCampeonato;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="campeonato")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Campeonato implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_campeonato")
    private Long idCampeonato;

    @Column(name="codigo", unique = true)
    private String codigo;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="aposta")
    private String aposta;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_fim", nullable = false)
    private OffsetDateTime dataFim;

    @Column(name="limite_times", nullable = false)
    private int limiteTimes;

    @Column(name="limite_participantes", nullable = false)
    private int limiteParticipantes;

    @Column(name="ativo", insertable = false)
    private boolean ativo;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_endereco", nullable = false)
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name="privacidade_campeonato", nullable = false)
    private TipoPrivacidadeCampeonato privacidadeCampeonato;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_academico", updatable = false, nullable = false)
    private Academico academico;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    @Enumerated(EnumType.STRING)
    @Column(name="situacao_campeonato", nullable = false)
    private TipoSituacaoCampeonato situacaoCampeonato;

    public void toEntity(CampeonatoDto dto) throws Exception {
        setTitulo(dto.titulo());
        setDescricao(dto.descricao());
        setAposta(dto.aposta());
        setDataInicio(dto.dataInicio());
        setDataFim(dto.dataFim());
        setLimiteTimes(dto.limiteTimes());
        setLimiteParticipantes(dto.limiteParticipantes());
        setAtivo(dto.ativo());

        switch (dto.privacidadeCampeonato()){
            case 0:
                setPrivacidadeCampeonato(TipoPrivacidadeCampeonato.PUBLICO);
                break;
            case 1:
                setPrivacidadeCampeonato(TipoPrivacidadeCampeonato.PRIVADO);
                break;
            default:
                throw new Exception("Tipo de privacidade invalido!");
        }

        switch (dto.situacaoCampeonato()){
            case 0:
                setSituacaoCampeonato(TipoSituacaoCampeonato.EM_ANDAMENTO);
                break;
            case 1:
                setSituacaoCampeonato(TipoSituacaoCampeonato.FINALIZADO);
                break;
            default:
                throw new Exception("Tipo de privacidade invalido!");
        }
    }
}
