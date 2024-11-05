package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportfy.sportfy.dtos.MetricasSistemaDto;
import com.sportfy.sportfy.services.EstatisticaService;

@RestController
@RequestMapping("/estatistica")
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
}
