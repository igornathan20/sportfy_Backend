package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportfy.sportfy.dtos.CanalDto;
import com.sportfy.sportfy.exeptions.ListaCanalVazioException;
import com.sportfy.sportfy.services.CanalService;

import java.util.List;

@RestController
@RequestMapping("/canal")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class CanalController {

    @Autowired
    private CanalService canalService;

    @GetMapping("/listarCanaisUsuario/{idUsuario}")
    //@PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<?> listarCanais(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<CanalDto> listaCanal = canalService.listarCanais(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(listaCanal);
        } catch (ListaCanalVazioException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
