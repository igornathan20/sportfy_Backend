package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.MetaDiariaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.MetaDiariaNaoExistenteException;
import com.sportfy.sportfy.models.MetaDiaria;
import com.sportfy.sportfy.services.MetaDiariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/metaDiaria")
public class MetaDiariaController {
    @Autowired
    private MetaDiariaService metaDiariaService;

    @PostMapping()
    public ResponseEntity<MetaDiaria> criarMeta(@RequestBody MetaDiariaDto metaDiariaDto) {
        try {
            MetaDiaria novaMeta = metaDiariaService.criarMeta(metaDiariaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
        } catch (AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<MetaDiaria> editarMeta(@RequestBody MetaDiariaDto metaDiariaDto) {
        try {
            MetaDiaria metaEditada = metaDiariaService.editarMeta(metaDiariaDto);
            return ResponseEntity.status(HttpStatus.OK).body(metaEditada);
        } catch (MetaDiariaNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MetaDiaria>> listarMetas() {
        try {
            List<MetaDiaria> metasDiarias = metaDiariaService.listarMetas();
            return ResponseEntity.status(HttpStatus.OK).body(metasDiarias);
        } catch (MetaDiariaNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<MetaDiaria>> buscarMeta(@PathVariable String nome) {
        try {
            List<Optional<MetaDiaria>> metasDiarias = metaDiariaService.buscarMeta(nome);
            return ResponseEntity.ok(
                    metasDiarias.stream().filter(Optional::isPresent).map(Optional::get).toList()
            );
        } catch (MetaDiariaNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Optional<MetaDiaria>> excluirMeta(@PathVariable Long id) {
        try {
            Optional<MetaDiaria> metaDiariaExcluida = metaDiariaService.excluirModalidade(id);
            return ResponseEntity.ok(metaDiariaExcluida);
        } catch (MetaDiariaNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
