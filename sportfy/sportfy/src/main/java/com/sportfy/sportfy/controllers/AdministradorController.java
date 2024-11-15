package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.AdministradorDto;
import com.sportfy.sportfy.dtos.AdministradorResponseDto;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaAdministradoresVaziaException;
import com.sportfy.sportfy.exeptions.OutroUsuarioComDadosJaExistentes;
import com.sportfy.sportfy.exeptions.PasswordInvalidoException;
import com.sportfy.sportfy.exeptions.PermissaoNaoExisteException;
import com.sportfy.sportfy.exeptions.RoleNaoPermitidaException;
import com.sportfy.sportfy.services.AdministradorService;
import jakarta.validation.Valid;
import lombok.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping(value = "/administrador")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequiredArgsConstructor
public class AdministradorController {

    @Autowired
    AdministradorService administradorService;

    @PostMapping("/cadastrar")
    //@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<AdministradorResponseDto> cadastrar(@RequestBody @Valid AdministradorDto administrador) {
        try {
            AdministradorResponseDto administradorCriado = administradorService.cadastrar(administrador);
            return ResponseEntity.status(HttpStatus.CREATED).body(administradorCriado);
        } catch (PasswordInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RoleNaoPermitidaException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PermissaoNaoExisteException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar/{idAdministrador}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<AdministradorResponseDto> atualizar(@PathVariable("idAdministrador") Long idAdministrador, @RequestBody @Valid AdministradorDto administrador) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            AdministradorResponseDto administradorAtualizado = administradorService.atualizar(idAdministrador, administrador, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(administradorAtualizado);
        } catch (AdministradorNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RoleNaoPermitidaException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PermissaoNaoExisteException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/inativar/{idAdministrador}")
    //@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<AdministradorResponseDto> inativar(@PathVariable("idAdministrador") Long idAdministrador) {
        try {
            AdministradorResponseDto administradorInativado = administradorService.inativar(idAdministrador);
            return ResponseEntity.status(HttpStatus.OK).body(administradorInativado);
        } catch (AdministradorNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultar/{idUsuario}")
    //@PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AdministradorResponseDto> consultar(@PathVariable("idUsuario") Long idUsuario) {
        try {
            AdministradorResponseDto administradorConsultado = administradorService.consultar(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(administradorConsultado);
        } catch (AdministradorNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    //@PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<AdministradorResponseDto>> listar(Pageable pageable) {
        try {
            Page<AdministradorResponseDto> listaAdministrador = administradorService.listar(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(listaAdministrador);
        } catch (ListaAdministradoresVaziaException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
