package com.sportfy.sportfy.controllers;

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
@RequiredArgsConstructor
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/consultar/{cep}")
    public ResponseEntity<Object> consultar(@PathVariable("cep") String cep) {
        try {
            Object enderecoConsultado  = enderecoService.consultar(cep);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoConsultado);
        } catch(CepNaoExisteException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(CepInvalidoException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
