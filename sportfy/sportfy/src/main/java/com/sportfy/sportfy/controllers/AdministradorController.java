package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaAdministradoresVaziaException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.exeptions.UsuarioJaExisteException;
import com.sportfy.sportfy.services.AdministradorService;
import lombok.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/administrador")
@RequiredArgsConstructor
public class AdministradorController {

    @Autowired
    AdministradorService administradorService;

    @PostMapping("/cadastrar")
    //@PreAuthorize("hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> cadastrar(@RequestBody AdministradorDto administrador) {
        try {
            Object administradorCriado = administradorService.cadastrar(administrador);
            return ResponseEntity.status(HttpStatus.CREATED).body(administradorCriado);
        } catch (UsuarioJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RoleNaoPermitidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(PermissaoNaoExisteException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/inativar/{idAdministrador}")
    //@PreAuthorize("hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> inativar(@PathVariable("idAdministrador") Long idAdministrador) {
        try {
            Object administradorInativado = administradorService.inativar(idAdministrador);
            return ResponseEntity.status(HttpStatus.OK).body(administradorInativado);
        } catch (AdministradorNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    //@PreAuthorize("hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<?> listar() {
        try {
            List<AdministradorDto> administradorLista = administradorService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(administradorLista);
        } catch (ListaAdministradoresVaziaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
