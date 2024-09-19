package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeJaExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.exeptions.RegistroNaoEncontradoException;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
import com.sportfy.sportfy.services.ModalidadeEsportivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/modalidadeEsportiva")
public class ModalidadeEsportivaController {
    @Autowired
    ModalidadeEsportivaService modalidadeEsportivaService;

    @PostMapping()
    public ResponseEntity<ModalidadeEsportiva> criarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            ModalidadeEsportiva novaModalidade = modalidadeEsportivaService.criarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
        } catch (ModalidadeJaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<ModalidadeEsportiva> editarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            ModalidadeEsportiva modalidadeEditada = modalidadeEsportivaService.editarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeEditada);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ModalidadeEsportiva>> listarModalidades() {
        try {
            List<ModalidadeEsportiva> modalidades = modalidadeEsportivaService.listarModalidades();
            return ResponseEntity.status(HttpStatus.OK).body(modalidades);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<Optional<ModalidadeEsportiva>> buscarModalidade(@PathVariable String nome) {
        try {
            Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaService.buscarModalidades(nome);
            return ResponseEntity.status(HttpStatus.OK).body(modalidade);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModalidadeEsportiva> excluirModalidade(@PathVariable Long id) {
        try {
            Optional<ModalidadeEsportiva> modalidadeRemovida = modalidadeEsportivaService.excluirModalidade(id);
            return modalidadeRemovida.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<ModalidadeEsportiva> desativarModalidade(@PathVariable Long id) {
        try {
            ModalidadeEsportiva modalidadeDesativada = modalidadeEsportivaService.desativarModalidade(id);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeDesativada);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/inscrever/{idAcademico}/{idModalidade}")
    public ResponseEntity<Void> inscreverEmModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.inscreverEmModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    @GetMapping("/listar/{idAcademico}")
    public ResponseEntity<List<ModalidadeEsportiva>> listarModalidadesAcademico(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportiva> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesInscritas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/remover/{idAcademico}/{idModalidade}")
    public ResponseEntity<Void> cancelarInscricaoModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.cancelarInscricaoModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{idModalidade}/estatisticas")
    public ResponseEntity<EstatisticasGeraisModalidadeDto> obterEstatisticasGeraisPorModalidade(@PathVariable Long idModalidade) {
        try {
            EstatisticasGeraisModalidadeDto estatisticas = modalidadeEsportivaService.estatisticasGeraisPorModalidade(idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (ModalidadeNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RegistroNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
