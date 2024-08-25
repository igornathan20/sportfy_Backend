package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportfy.sportfy.exeptions.CepInvalidoException;
import com.sportfy.sportfy.exeptions.CepNaoExisteException;
import com.sportfy.sportfy.services.EnderecoService;

import jakarta.annotation.security.PermitAll;
import lombok.*;

@RestController
@RequestMapping(value = "/endereco")
@RequiredArgsConstructor
public class EnderecoController {
    
    @Autowired
    EnderecoService enderecoService;

    @PermitAll
    @GetMapping("/consultar/{cep}")
    public ResponseEntity<Object> consultar(@PathVariable("cep") String cep) {
        try {
            Object enderecoConsultado  = enderecoService.consultar(cep);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoConsultado);
        } catch(CepNaoExisteException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch(CepInvalidoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
