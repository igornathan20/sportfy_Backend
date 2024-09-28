package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeJaExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.services.ModalidadeEsportivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/modalidadeEsportiva")
public class ModalidadeEsportivaController {
    @Autowired
    ModalidadeEsportivaService modalidadeEsportivaService;

    @PostMapping()
    public ResponseEntity<Object> criarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            Object novaModalidade = modalidadeEsportivaService.criarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
        } catch (ModalidadeJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<Object> editarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            Object modalidadeEditada = modalidadeEsportivaService.editarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeEditada);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ModalidadeJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarModalidades() {
        try {
            List<ModalidadeEsportivaDto> modalidades = modalidadeEsportivaService.listarModalidades();
            return ResponseEntity.status(HttpStatus.OK).body(modalidades);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<Object> buscarModalidade(@PathVariable String nome) {
        try {
            Object modalidade = modalidadeEsportivaService.buscarModalidades(nome);
            return ResponseEntity.status(HttpStatus.OK).body(modalidade);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> desativarModalidade(@PathVariable Long id) {
        try {
            Object modalidadeDesativada = modalidadeEsportivaService.desativarModalidade(id);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeDesativada);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/inscrever/{idAcademico}/{idModalidade}")
    public ResponseEntity<Object> inscreverEmModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.inscreverEmModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @DeleteMapping("/remover/{idAcademico}/{idModalidade}")
    public ResponseEntity<Object> cancelarInscricaoModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.cancelarInscricaoModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
