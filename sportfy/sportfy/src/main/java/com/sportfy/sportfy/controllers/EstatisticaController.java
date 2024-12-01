package com.sportfy.sportfy.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.sportfy.sportfy.dtos.EstatisticasMetasEsportivasDto;
import com.sportfy.sportfy.dtos.MetricasSistemaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ConteudoPrivadoException;
import com.sportfy.sportfy.services.EstatisticaService;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/estatistica")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class EstatisticaController {

    private static final Logger logger = LoggerFactory.getLogger(EstatisticaController.class);
    @Autowired
    private EstatisticaService estatisticaService;

    @GetMapping("/visualizarMetricasSistema")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<MetricasSistemaDto> metricasSistema() {
        try {
            MetricasSistemaDto metricasSistema = estatisticaService.metricasSistema();
            return ResponseEntity.status(HttpStatus.OK).body(metricasSistema);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/visualizarEstatisticasMetasEsportivas/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<EstatisticasMetasEsportivasDto> visualizarEstatisticasMetasEsportivas(@PathVariable Long idAcademico) {
        try {
            EstatisticasMetasEsportivasDto estatisticasMetasEsportivas = estatisticaService.visualizarEstatisticasMetasEsportivas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(estatisticasMetasEsportivas);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/visualizarEstatisticasMetasEsportivasOutroAcademico/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<EstatisticasMetasEsportivasDto> visualizarEstatisticasMetasEsportivasOutroAcademico(@PathVariable Long idAcademico) {
        try {
            EstatisticasMetasEsportivasDto estatisticasMetasEsportivas = estatisticaService.visualizarEstatisticasMetasEsportivasOutroAcademico(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(estatisticasMetasEsportivas);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ConteudoPrivadoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
