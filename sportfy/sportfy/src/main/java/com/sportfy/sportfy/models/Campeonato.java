package com.sportfy.sportfy.models;

import java.io.Serializable;

import com.sportfy.sportfy.dtos.CampeonatoDto;
import com.sportfy.sportfy.dtos.EnderecoDto;
import com.sportfy.sportfy.enums.TipoFasePartida;
import com.sportfy.sportfy.enums.TipoPrivacidadeCampeonato;
import com.sportfy.sportfy.enums.TipoSituacao;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name="campeonato")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Campeonato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campeonato")
    private Long idCampeonato;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "senha", unique = true)
    private String senha;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "aposta")
    private String aposta;

    @CreationTimestamp
    @Column(name = "data_criacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private OffsetDateTime dataFim;

    @Column(name = "limite_times", nullable = false)
    private int limiteTimes;

    @Column(name = "limite_participantes", nullable = false)
    private int limiteParticipantes;

    @Column(name = "ativo", insertable = false)
    private boolean ativo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacidade_campeonato", nullable = false)
    private TipoPrivacidadeCampeonato privacidadeCampeonato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_academico", updatable = false, nullable = false)
    private Academico academico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modalidade_esportiva", updatable = false, nullable = false)
    private ModalidadeEsportiva modalidadeEsportiva;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_campeonato", nullable = false)
    private TipoSituacao situacaoCampeonato;

    @Enumerated(EnumType.STRING)
    @Column(name = "fase_atual")
    private TipoFasePartida faseAtual;

    @ManyToMany
    @JoinTable(
            name = "campeonato_partida",
            joinColumns = @JoinColumn(name = "id_partida"),
            inverseJoinColumns = @JoinColumn(name = "id_campeonato")
    )
    private List<Partida> partidas;

    public void toEntity(CampeonatoDto dto) throws Exception {
        if (dto.titulo() != null) {
            setTitulo(dto.titulo());
        }

        if (dto.descricao() != null) {
            setDescricao(dto.descricao());
        }

        if (dto.aposta() != null) {
            setAposta(dto.aposta());
        }

        if (dto.dataInicio() != null) {
            setDataInicio(dto.dataInicio());
        }

        if (dto.dataFim() != null) {
            setDataFim(dto.dataFim());
        }

        if (dto.limiteTimes() != 0) {
            setLimiteTimes(dto.limiteTimes());
        }

        if (dto.limiteParticipantes() != 0) {
            setLimiteParticipantes(dto.limiteParticipantes());
        }

        setAtivo(dto.ativo());


        switch (dto.privacidadeCampeonato()) {
            case 0:
                setPrivacidadeCampeonato(TipoPrivacidadeCampeonato.PUBLICO);
                break;
            case 1:
                setPrivacidadeCampeonato(TipoPrivacidadeCampeonato.PRIVADO);
                break;
            default:
                throw new Exception("Tipo de privacidade invalido!");
        }

         switch (dto.situacaoCampeonato()) {
                case 0:
                    setSituacaoCampeonato(TipoSituacao.EM_ABERTO);
                    break;
                case 1:
                    setSituacaoCampeonato(TipoSituacao.INICIADO);
                    break;
                case 2:
                    setSituacaoCampeonato(TipoSituacao.FINALIZADO);
                    break;
                default:
                    throw new Exception("Situação do campeonato inválida!");
         }
    }


    public CampeonatoDto toDto(Campeonato campeonato) {
        return new CampeonatoDto(
                campeonato.getIdCampeonato(),
                campeonato.getCodigo(),
                "",
                campeonato.getTitulo(),
                campeonato.getDescricao(),
                campeonato.getAposta(),
                campeonato.getDataCriacao(),
                campeonato.getDataInicio(),
                campeonato.getDataFim(),
                campeonato.getLimiteTimes(),
                campeonato.getLimiteParticipantes(),
                campeonato.isAtivo(),
                EnderecoDto.fromEntity(campeonato.getEndereco()),
                campeonato.getPrivacidadeCampeonato().ordinal(),
                campeonato.academico.getIdAcademico(),
                campeonato.getModalidadeEsportiva().getIdModalidadeEsportiva(),
                campeonato.getSituacaoCampeonato().ordinal()
        );
    }

}


