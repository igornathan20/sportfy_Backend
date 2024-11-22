package com.sportfy.sportfy.models;

import java.io.Serializable;
import com.sportfy.sportfy.dtos.CampeonatoDto;
import com.sportfy.sportfy.dtos.CampeonatoResponseDto;
import com.sportfy.sportfy.dtos.EnderecoDto;
import com.sportfy.sportfy.enums.TipoFasePartida;
import com.sportfy.sportfy.enums.TipoPrivacidadeCampeonato;
import com.sportfy.sportfy.enums.TipoSituacao;
import com.sportfy.sportfy.exeptions.TipoInvalidoException;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    public void toEntity(CampeonatoDto dto) throws TipoInvalidoException {
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
            OffsetDateTime nowInUTC = OffsetDateTime.now(ZoneOffset.UTC);
            if (dto.dataFim().isBefore(nowInUTC)) {
                throw new TipoInvalidoException("Data de fim não pode ser no passado!");
            }
            setDataFim(dto.dataFim());
        }

        if (dto.limiteTimes() != 0 && dto.limiteTimes() < 17) {
            setLimiteTimes(dto.limiteTimes());
        }else {
            throw new TipoInvalidoException("Limite de times invalido!");
        }

        if (dto.limiteParticipantes() != 0) {
            setLimiteParticipantes(dto.limiteParticipantes());
        }

        setAtivo(dto.ativo());


        switch (dto.privacidadeCampeonato()) {
            case "PUBLICO":
                setPrivacidadeCampeonato(TipoPrivacidadeCampeonato.PUBLICO);
                break;
            case "PRIVADO":
                setPrivacidadeCampeonato(TipoPrivacidadeCampeonato.PRIVADO);
                break;
            default:
                throw new TipoInvalidoException("Tipo de privacidade invalido!");
        }

         switch (dto.situacaoCampeonato()) {
                case "EM_ABERTO":
                    setSituacaoCampeonato(TipoSituacao.EM_ABERTO);
                    break;
                case "INICIADO":
                    setSituacaoCampeonato(TipoSituacao.INICIADO);
                    break;
                case "FINALIZADO":
                    setSituacaoCampeonato(TipoSituacao.FINALIZADO);
                    break;
                default:
                    throw new TipoInvalidoException("Situação do campeonato inválida!");
         }
    }


    public CampeonatoResponseDto toResponseDto(Campeonato campeonato) {
        return new CampeonatoResponseDto(
                campeonato.getIdCampeonato(),
                campeonato.getCodigo(),
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
                campeonato.getPrivacidadeCampeonato().name(),
                campeonato.academico.getIdAcademico(),
                campeonato.academico.getUsuario().getUsername(),
                campeonato.getModalidadeEsportiva().getIdModalidadeEsportiva(),
                campeonato.getSituacaoCampeonato().name()
        );
    }

}


