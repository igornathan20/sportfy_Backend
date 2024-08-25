package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.exeptions.EmailInvalidoException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.services.AcademicoService;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/academico")
@RequiredArgsConstructor
public class AcademicoController {

    @Autowired
    AcademicoService academicoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody AcademicoDto academico){
        try {
            Object academicoCriado = academicoService.cadastrar(academico);
            return ResponseEntity.status(HttpStatus.CREATED).body(academicoCriado);
        }catch (UsuarioJaExisteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch(EmailInvalidoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(PermissaoNaoExisteException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
