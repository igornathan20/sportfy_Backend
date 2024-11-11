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
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class MetaDiariaController {
    @Autowired
    private MetaDiariaService metaDiariaService;

    @PostMapping()
    public ResponseEntity<MetaDiariaDto> criarMeta(@RequestBody MetaDiariaDto metaDiariaDto) {
        try {
            MetaDiariaDto novaMeta = metaDiariaService.criarMeta(metaDiariaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    public ResponseEntity<MetaDiariaDto> editarMeta(@RequestBody MetaDiariaDto metaDiariaDto) {
        try {
            MetaDiariaDto metaEditada = metaDiariaService.editarMeta(metaDiariaDto);
            return ResponseEntity.status(HttpStatus.OK).body(metaEditada);
        } catch (MetaDiariaNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar/{idAcademico}")
    public ResponseEntity<List<MetaDiariaDto>> listarMetas(@PathVariable Long idAcademico) {
        try {
            List<MetaDiariaDto> metasDiarias = metaDiariaService.listarMetas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(metasDiarias);
        }catch (AcademicoNaoExisteException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (MetaDiariaNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/buscar/{nome}")
    public ResponseEntity<List<MetaDiariaDto>> buscarMeta(@PathVariable Long idAcademico,@PathVariable String nome) {
        try {
            List<MetaDiariaDto> metasDiarias = metaDiariaService.buscarMeta(idAcademico, nome);
            return ResponseEntity.status(HttpStatus.OK).body(metasDiarias);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (MetaDiariaNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<MetaDiariaDto> excluirMeta(@PathVariable Long id) {
        try {
            MetaDiariaDto metaDiariaExcluida = metaDiariaService.excluirModalidade(id);
            return ResponseEntity.ok(metaDiariaExcluida);
        } catch (MetaDiariaNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
