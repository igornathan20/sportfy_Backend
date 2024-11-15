package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.sportfy.sportfy.dtos.MetaEsportivaConquistaDto;
import com.sportfy.sportfy.exeptions.ConquistaJaExistenteException;
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

    @Autowired
    private ConquistaService conquistaService;

    @PostMapping("/conquistar/{idAcademico}/{idMetaEsportiva}")
    //@PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> conquistar(@PathVariable("idAcademico") Long idAcademico, @PathVariable("idMetaEsportiva") Long idMetaEsportiva) {
        try {
            Object novaConquista = conquistaService.conquistar(idAcademico, idMetaEsportiva);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConquista);
        } catch (ConquistaJaExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listarConquistas/{idAcademico}")
    //@PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<?> listarConquistas(@PathVariable("idAcademico") Long idAcademico) {
        try {
            List<MetaEsportivaConquistaDto> listaConquista = conquistaService.listarConquistas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(listaConquista);
        } catch (MetaEsportivaNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
