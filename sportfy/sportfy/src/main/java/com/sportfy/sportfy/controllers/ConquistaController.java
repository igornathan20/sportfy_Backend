package com.sportfy.sportfy.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.sportfy.sportfy.dtos.ConquistaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ConquistaNaoExistenteException;
import com.sportfy.sportfy.exeptions.ConteudoPrivadoException;
import com.sportfy.sportfy.exeptions.MetaEsportivaNaoExisteException;
import com.sportfy.sportfy.services.ConquistaService;

@RestController
@RequestMapping("/conquista")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class ConquistaController {

    private static final Logger logger = LoggerFactory.getLogger(ConquistaController.class);

    @Autowired
    private ConquistaService conquistaService;

    @PutMapping("/atualizarConquista")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> atualizarConquista(@RequestBody ConquistaDto conquistaDto) {
        try {
            Object conquistaAtualizada = conquistaService.atualizarConquista(conquistaDto);
            return ResponseEntity.status(HttpStatus.OK).body(conquistaAtualizada);
        } catch (ConquistaNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listarConquistas/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ConquistaDto>> listarConquistas(@PathVariable("idAcademico") Long idAcademico) {
        try {
            List<ConquistaDto> listaConquista = conquistaService.listarConquistas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(listaConquista);
        } catch (MetaEsportivaNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listarConquistasOutroAcademico/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ConquistaDto>> listarConquistasOutroAcademico(@PathVariable("idAcademico") Long idAcademico) {
        try {
            List<ConquistaDto> listaConquista = conquistaService.listarConquistasOutroAcademico(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(listaConquista);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ConteudoPrivadoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (MetaEsportivaNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
