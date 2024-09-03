package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.ApoioSaudeDto;
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
    public ResponseEntity<ApoioSaude> criarApoioSaude(@RequestBody ApoioSaudeDto apoioSaudeDto) {
        try {
            ApoioSaude novoApoioSaude = apoioSaudeService.criarMeta(apoioSaudeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoApoioSaude);
        } catch (AdministradorNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<ApoioSaude> editarApoioSaude(@RequestBody ApoioSaudeDto apoioSaudeDto) {
        try {
            ApoioSaude apoioSaudeEditado = apoioSaudeService.editarApoioSaude(apoioSaudeDto);
            return ResponseEntity.status(HttpStatus.OK).body(apoioSaudeEditado);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ApoioSaude>> listarApoioSaude() {
        try {
            List<ApoioSaude> listaApoioSaude = apoioSaudeService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(listaApoioSaude);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<Optional<ApoioSaude>>> buscarApoioSaude(@PathVariable String nome) {
        try {
            List<Optional<ApoioSaude>> registrosEncontrados = apoioSaudeService.buscarApoioSaude(nome);
            return ResponseEntity.status(HttpStatus.OK).body(registrosEncontrados);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<ApoioSaude>> excluirApoioSaude(@PathVariable Long id) {
        try {
            Optional<ApoioSaude> apoioSaudeExcluido = apoioSaudeService.excluirApoioSaude(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apoioSaudeExcluido);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        }
    }
}