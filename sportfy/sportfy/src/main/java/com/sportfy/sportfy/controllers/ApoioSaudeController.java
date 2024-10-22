package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.ApoioSaudeDto;
import com.sportfy.sportfy.dtos.ApoioSaudeResponseDto;
import com.sportfy.sportfy.exeptions.AdministradorNaoExisteException;
import com.sportfy.sportfy.exeptions.RegistroNaoEncontradoException;
import com.sportfy.sportfy.models.ApoioSaude;
import com.sportfy.sportfy.services.ApoioSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apoioSaude")
public class ApoioSaudeController {
    @Autowired
    private ApoioSaudeService apoioSaudeService;

    @PostMapping
    public ResponseEntity<ApoioSaudeResponseDto> criarApoioSaude(@RequestBody ApoioSaudeDto apoioSaudeDto) {
        try {
            ApoioSaudeResponseDto novoApoioSaude = apoioSaudeService.criarApoioSaude(apoioSaudeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoApoioSaude);
        } catch (AdministradorNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{idApoioSaude}")
    public ResponseEntity<ApoioSaudeResponseDto> editarApoioSaude(@PathVariable Long idApoioSaude , @RequestBody ApoioSaudeDto apoioSaudeDto) {
        try {
            ApoioSaudeResponseDto apoioSaudeEditado = apoioSaudeService.editarApoioSaude(idApoioSaude, apoioSaudeDto);
            return ResponseEntity.status(HttpStatus.OK).body(apoioSaudeEditado);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ApoioSaudeResponseDto>> listarApoioSaude() {
        try {
            List<ApoioSaudeResponseDto> listaApoioSaude = apoioSaudeService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(listaApoioSaude);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<ApoioSaudeResponseDto>> buscarApoioSaude(@PathVariable String nome) {
        try {
            List<ApoioSaudeResponseDto> registrosEncontrados = apoioSaudeService.buscarApoioSaude(nome);
            return ResponseEntity.status(HttpStatus.OK).body(registrosEncontrados);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApoioSaudeResponseDto> excluirApoioSaude(@PathVariable Long id) {
        try {
            ApoioSaudeResponseDto apoioSaudeExcluido = apoioSaudeService.excluirApoioSaude(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apoioSaudeExcluido);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}