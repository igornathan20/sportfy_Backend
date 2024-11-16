package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.EnderecoDto;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportfy.sportfy.exeptions.CepInvalidoException;
import com.sportfy.sportfy.exeptions.CepNaoExisteException;
import com.sportfy.sportfy.services.EnderecoService;
import lombok.*;

@RestController
@RequestMapping(value = "/endereco")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequiredArgsConstructor
public class EnderecoController {
    
    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);
    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/consultar/{cep}")
    @PermitAll
    public ResponseEntity<EnderecoDto> consultar(@PathVariable("cep") String cep) {
        try {
            EnderecoDto enderecoConsultado  = enderecoService.consultar(cep);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoConsultado);
        } catch(CepNaoExisteException | CepInvalidoException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch(Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
