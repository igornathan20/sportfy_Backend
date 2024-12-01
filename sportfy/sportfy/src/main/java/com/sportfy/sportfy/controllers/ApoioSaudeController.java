package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.ApoioSaudeDto;
import com.sportfy.sportfy.dtos.ApoioSaudeResponseDto;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.RegistroNaoEncontradoException;
import com.sportfy.sportfy.services.ApoioSaudeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/apoioSaude")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class ApoioSaudeController {

    private static final Logger logger = LoggerFactory.getLogger(ApoioSaudeController.class);
    @Autowired
    private ApoioSaudeService apoioSaudeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ApoioSaudeResponseDto> criarApoioSaude(@RequestBody ApoioSaudeDto apoioSaudeDto) {
        try {
            ApoioSaudeResponseDto novoApoioSaude = apoioSaudeService.criarApoioSaude(apoioSaudeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoApoioSaude);
        } catch (AdministradorNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{idApoioSaude}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ApoioSaudeResponseDto> editarApoioSaude(@PathVariable Long idApoioSaude , @RequestBody ApoioSaudeDto apoioSaudeDto) {
        try {
            ApoioSaudeResponseDto apoioSaudeEditado = apoioSaudeService.editarApoioSaude(idApoioSaude, apoioSaudeDto);
            return ResponseEntity.status(HttpStatus.OK).body(apoioSaudeEditado);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ApoioSaudeResponseDto>> listarApoioSaude() {
        try {
            List<ApoioSaudeResponseDto> listaApoioSaude = apoioSaudeService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(listaApoioSaude);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{nome}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ApoioSaudeResponseDto>> buscarApoioSaude(@PathVariable String nome) {
        try {
            List<ApoioSaudeResponseDto> registrosEncontrados = apoioSaudeService.buscarApoioSaude(nome);
            return ResponseEntity.status(HttpStatus.OK).body(registrosEncontrados);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ApoioSaudeResponseDto> excluirApoioSaude(@PathVariable Long id) {
        try {
            ApoioSaudeResponseDto apoioSaudeExcluido = apoioSaudeService.excluirApoioSaude(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apoioSaudeExcluido);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ApoioSaudeResponseDto> desativarApoioSaude(@PathVariable Long id) {
        try {
            ApoioSaudeResponseDto apoioSaude = apoioSaudeService.desativarApoioSaude(id);
            return ResponseEntity.status(HttpStatus.OK).body(apoioSaude);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}