package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.services.AdministradorService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/adm")
@RequiredArgsConstructor
public class AdministradorController {
    @Autowired
    AdministradorService administradorService;

    @PostMapping
    @PermitAll
    //@PreAuthorize("hasRole('ROLE_Administrador')")
    public ResponseEntity<Object> create(@RequestBody AdministradorDto administrador) {
        try {
            Object administradorCriado = administradorService.novoAdministrador(administrador);
            return ResponseEntity.status(HttpStatus.CREATED).body(administradorCriado);
        } catch (UsuarioJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RoleNaoPermitidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}