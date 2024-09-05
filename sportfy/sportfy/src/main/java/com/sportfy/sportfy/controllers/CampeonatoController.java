package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.CampeonatoDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.exeptions.RegistroNaoEncontradoException;
import com.sportfy.sportfy.models.Campeonato;
import com.sportfy.sportfy.services.CampeonatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {
    @Autowired
    private CampeonatoService campeonatoService;

    @PostMapping
    public ResponseEntity<Campeonato> criarCampeonato(@RequestBody CampeonatoDto campeonatoDto) {
        try {
            Campeonato campeonato = campeonatoService.criarCampeonato(campeonatoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(campeonato);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<Campeonato> editarCampeonato(@RequestBody CampeonatoDto campeonatoDto) {
        try {
            Campeonato campeonato = campeonatoService.editarCampeonato(campeonatoDto);
            return ResponseEntity.status(HttpStatus.OK).body(campeonato);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Campeonato>> listarTodosCampeonatos() {
        try {
            List<Campeonato> campeonatos = campeonatoService.listarTodosCampeonatos();
            return new ResponseEntity<>(campeonatos, HttpStatus.OK);
        } catch (RegistroNaoEncontradoException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Campeonato>> listarCampeonatosComFiltro(
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String aposta,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataCriacao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @RequestParam(required = false) Integer limiteTimes,
            @RequestParam(required = false) Integer limiteParticipantes,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer privacidadeCampeonato,
            @RequestParam(required = false) Long idAcademico,
            @RequestParam(required = false) Long idModalidadeEsportiva,
            @RequestParam(required = false) Integer situacaoCampeonato) {
        try {
            CampeonatoDto campeonatoDto = new CampeonatoDto(
                    null, codigo, titulo, descricao, aposta, dataCriacao, dataInicio, dataFim,
                    limiteTimes != null ? limiteTimes : 0,
                    limiteParticipantes != null ? limiteParticipantes : 0,
                    ativo != null ? ativo : false,
                    null, privacidadeCampeonato != null ? privacidadeCampeonato : 0,
                    idAcademico, idModalidadeEsportiva,
                    situacaoCampeonato != null ? situacaoCampeonato : 0
            );

            List<Campeonato> campeonatos = campeonatoService.listarCampeonatosComFiltro(campeonatoDto);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Campeonato> excluirCampeonato(@PathVariable Long id) {
        try {
            Optional<Campeonato> campeonato = campeonatoService.excluirCampeonato(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(campeonato.get());
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Campeonato> desativarCampeonato(@PathVariable Long id) {
        try {
            Campeonato campeonato = campeonatoService.desativarCampeonato(id);
            return ResponseEntity.status(HttpStatus.OK).body(campeonato);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
