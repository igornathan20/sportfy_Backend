package com.sportfy.sportfy.services;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.EstatisticaPorModalidadeEsportivaDto;
import com.sportfy.sportfy.dtos.EstatisticasMetasEsportivasDto;
import com.sportfy.sportfy.dtos.MetricasSistemaDto;
import com.sportfy.sportfy.enums.TipoSituacao;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ConteudoPrivadoException;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.AcademicoModalidadeEsportiva;
import com.sportfy.sportfy.models.MetaEsportiva;
import com.sportfy.sportfy.repositories.AcademicoModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.CampeonatoRepository;
import com.sportfy.sportfy.repositories.ConquistaRepository;
import com.sportfy.sportfy.repositories.JogadorRepository;
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.PrivacidadeRepository;
import com.sportfy.sportfy.repositories.PublicacaoRepository;
import com.sportfy.sportfy.repositories.UsuarioRepository;

@Service
public class EstatisticaService {

    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;

    @Autowired
    AcademicoModalidadeEsportivaRepository academicoModalidadeEsportivaRepository;

    @Autowired
    MetaEsportivaRepository metaEsportivaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AcademicoRepository academicoRepository;

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Autowired
    CampeonatoRepository campeonatoRepository;

    @Autowired
    ConquistaRepository conquistaRepository;

    @Autowired
    JogadorRepository jogadorRepository;

    @Autowired
    private PrivacidadeRepository privacidadeRepository;

    public MetricasSistemaDto metricasSistema() {
        Integer quantidadeModalidadesCadastradas = 0;
        Integer quantidadeAcademicosCadastrados = 0;
        Integer quantidadePublicacoes = 0;
        Integer totalCampeonatosAbertos = 0;
        Integer totalCampeonatosFinalizados = 0;

        OffsetDateTime dataInicio = OffsetDateTime.now().minus(30, ChronoUnit.DAYS);
        quantidadeModalidadesCadastradas = modalidadeEsportivaRepository.findByDataCriacaoInLast30Days(dataInicio).size();
        quantidadeAcademicosCadastrados = academicoRepository.findByUsuarioDataCriacaoInLast30Days(dataInicio).size();
        quantidadePublicacoes = publicacaoRepository.findByDataPublicacaoInLast30Days(dataInicio).size();
        totalCampeonatosAbertos = campeonatoRepository.findBySituacaoAndDataCriacaoInLast30Days(TipoSituacao.INICIADO, dataInicio).size();
        totalCampeonatosAbertos += campeonatoRepository.findBySituacaoAndDataCriacaoInLast30Days(TipoSituacao.EM_ABERTO, dataInicio).size();
        totalCampeonatosFinalizados = campeonatoRepository.findBySituacaoAndDataCriacaoInLast30Days(TipoSituacao.FINALIZADO, dataInicio).size();

        MetricasSistemaDto metricasSistema = new MetricasSistemaDto(
            quantidadeModalidadesCadastradas,
            quantidadeAcademicosCadastrados,
            quantidadePublicacoes,
            totalCampeonatosAbertos,
            totalCampeonatosFinalizados
        );
        return metricasSistema;
    }

    public EstatisticasMetasEsportivasDto visualizarEstatisticasMetasEsportivas(Long idAcademico) {
        Integer totalModalidadesEsportivasInscritas = 0; 
        Integer totalMetasEsportivasInscritas = 0;
        Integer totalConquistasAlcancadas = conquistaRepository.findByAcademicoIdAcademicoAndConquistadoAndAtivo(idAcademico, true, true).size();
        Integer totalCampeonatosCriados = campeonatoRepository.findByAcademicoIdAcademico(idAcademico).size();
        Integer totalCampeonatosParticipados = jogadorRepository.findByAcademicoIdAcademico(idAcademico).size();
        List<EstatisticaPorModalidadeEsportivaDto> listaEstatisticaPorModalidadeEsportivaDto = new ArrayList<>();

        List<AcademicoModalidadeEsportiva> listaAcademicoModalidadeEsportiva = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaAtivo(idAcademico, true);
        for (AcademicoModalidadeEsportiva academicoModalidadeEsportiva : listaAcademicoModalidadeEsportiva) {
            List<Long> idMetaEsportivas = new ArrayList<>();
            for (MetaEsportiva metaEsportiva : academicoModalidadeEsportiva.getModalidadeEsportiva().getListaMetaEsportiva()) {
                idMetaEsportivas.add(metaEsportiva.getIdMetaEsportiva());
            }
            EstatisticaPorModalidadeEsportivaDto estatisticaPorModalidadeEsportivaDto = new EstatisticaPorModalidadeEsportivaDto(
                academicoModalidadeEsportiva.getModalidadeEsportiva().getNome(), 
                academicoModalidadeEsportiva.getModalidadeEsportiva().getFoto(),
                academicoModalidadeEsportiva.getModalidadeEsportiva().getListaMetaEsportiva().size(), 
                conquistaRepository.findConqueredByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportivaIn(idAcademico, idMetaEsportivas).size(),
                campeonatoRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico, academicoModalidadeEsportiva.getModalidadeEsportiva().getIdModalidadeEsportiva()).size(),
                jogadorRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico, academicoModalidadeEsportiva.getModalidadeEsportiva().getIdModalidadeEsportiva()).size()
            );

            totalModalidadesEsportivasInscritas++;
            totalMetasEsportivasInscritas += academicoModalidadeEsportiva.getModalidadeEsportiva().getListaMetaEsportiva().size();
            listaEstatisticaPorModalidadeEsportivaDto.add(estatisticaPorModalidadeEsportivaDto);
        }
        return new EstatisticasMetasEsportivasDto(totalModalidadesEsportivasInscritas, totalMetasEsportivasInscritas, totalConquistasAlcancadas, totalCampeonatosCriados, totalCampeonatosParticipados, listaEstatisticaPorModalidadeEsportivaDto);
    }

    public EstatisticasMetasEsportivasDto visualizarEstatisticasMetasEsportivasOutroAcademico(Long idAcademico) throws AcademicoNaoExisteException, ConteudoPrivadoException {
        Optional<Academico> academicoBD = academicoRepository.findById(idAcademico);
        if (academicoBD.isEmpty()) {
            throw new AcademicoNaoExisteException("Acadêmico não existe!");
        }

        boolean mostrarEstatisticasModalidadesEsportivas = privacidadeRepository.findByIdAcademico(academicoBD.get().getIdAcademico()).isMostrarEstatisticasModalidadesEsportivas();
        if (!mostrarEstatisticasModalidadesEsportivas) {
            throw new ConteudoPrivadoException("Acadêmico não permite mostrar estatisticas de modalidades esportivas!");
        }
        
        Integer totalModalidadesEsportivasInscritas = 0; 
        Integer totalMetasEsportivasInscritas = 0;
        Integer totalConquistasAlcancadas = conquistaRepository.findByAcademicoIdAcademicoAndConquistadoAndAtivo(idAcademico, true, true).size();
        Integer totalCampeonatosCriados = campeonatoRepository.findByAcademicoIdAcademico(idAcademico).size();
        Integer totalCampeonatosParticipados = jogadorRepository.findByAcademicoIdAcademico(idAcademico).size();
        List<EstatisticaPorModalidadeEsportivaDto> listaEstatisticaPorModalidadeEsportivaDto = new ArrayList<>();

        List<AcademicoModalidadeEsportiva> listaAcademicoModalidadeEsportiva = academicoModalidadeEsportivaRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaAtivo(idAcademico, true);
        for (AcademicoModalidadeEsportiva academicoModalidadeEsportiva : listaAcademicoModalidadeEsportiva) {
            List<Long> idMetaEsportivas = new ArrayList<>();
            for (MetaEsportiva metaEsportiva : academicoModalidadeEsportiva.getModalidadeEsportiva().getListaMetaEsportiva()) {
                idMetaEsportivas.add(metaEsportiva.getIdMetaEsportiva());
            }
            EstatisticaPorModalidadeEsportivaDto estatisticaPorModalidadeEsportivaDto = new EstatisticaPorModalidadeEsportivaDto(
                academicoModalidadeEsportiva.getModalidadeEsportiva().getNome(), 
                academicoModalidadeEsportiva.getModalidadeEsportiva().getFoto(),
                academicoModalidadeEsportiva.getModalidadeEsportiva().getListaMetaEsportiva().size(), 
                conquistaRepository.findConqueredByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportivaIn(idAcademico, idMetaEsportivas).size(),
                campeonatoRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico, academicoModalidadeEsportiva.getModalidadeEsportiva().getIdModalidadeEsportiva()).size(),
                jogadorRepository.findByAcademicoIdAcademicoAndModalidadeEsportivaIdModalidadeEsportiva(idAcademico, academicoModalidadeEsportiva.getModalidadeEsportiva().getIdModalidadeEsportiva()).size()
            );

            totalModalidadesEsportivasInscritas++;
            totalMetasEsportivasInscritas += academicoModalidadeEsportiva.getModalidadeEsportiva().getListaMetaEsportiva().size();
            listaEstatisticaPorModalidadeEsportivaDto.add(estatisticaPorModalidadeEsportivaDto);
        }
        return new EstatisticasMetasEsportivasDto(totalModalidadesEsportivasInscritas, totalMetasEsportivasInscritas, totalConquistasAlcancadas, totalCampeonatosCriados, totalCampeonatosParticipados, listaEstatisticaPorModalidadeEsportivaDto);
    }
}
