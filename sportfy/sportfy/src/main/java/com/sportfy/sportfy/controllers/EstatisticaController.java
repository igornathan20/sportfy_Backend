package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sportfy.sportfy.dtos.EstatisticasMetasEsportivasDto;
import com.sportfy.sportfy.dtos.MetricasSistemaDto;
import com.sportfy.sportfy.services.EstatisticaService;

@RestController
@RequestMapping("/estatistica")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class EstatisticaController {

    @Autowired
    private EstatisticaService estatisticaService;

    @GetMapping("/visualizarMetricasSistema")
    //@PreAuthorize(hasRole('ROLE_ADMINISTRADOR'))
    public ResponseEntity<?> metricasSistema() {
        try {
            MetricasSistemaDto metricasSistema = estatisticaService.metricasSistema();
            return ResponseEntity.status(HttpStatus.OK).body(metricasSistema);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/visualizarEstatisticasMetasEsportivas/{idAcademico}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<?> visualizarEstatisticasMetasEsportivas(@PathVariable Long idAcademico) {
        try {
            EstatisticasMetasEsportivasDto estatisticasMetasEsportivas = estatisticaService.visualizarEstatisticasMetasEsportivas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(estatisticasMetasEsportivas);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
