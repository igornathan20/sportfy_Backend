package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.exeptions.EmailInvalidoException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.services.AcademicoService;
import jakarta.annotation.security.PermitAll;
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

    @PostMapping
    @PermitAll
    public ResponseEntity<Object> create(@RequestBody AcademicoDto academico){
        try {
            Object usuarioCriado  = academicoService.novoAcademico(academico);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        }catch (UsuarioJaExisteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch(EmailInvalidoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch(PermissaoNaoExisteException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}