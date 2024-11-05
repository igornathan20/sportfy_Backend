package com.sportfy.sportfy.services;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.MetricasSistemaDto;
import com.sportfy.sportfy.enums.TipoSituacao;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.CampeonatoRepository;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import com.sportfy.sportfy.repositories.PublicacaoRepository;
import com.sportfy.sportfy.repositories.UsuarioRepository;

@Service
public class EstatisticaService {

    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AcademicoRepository academicoRepository;

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Autowired
    CampeonatoRepository campeonatoRepository;

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
}
