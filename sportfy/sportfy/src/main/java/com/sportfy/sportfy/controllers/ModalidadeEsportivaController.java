package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.ModalidadeEsportiva;
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
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<Object> editarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            Object modalidadeEditada = modalidadeEsportivaService.editarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeEditada);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ModalidadeJaExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarModalidades() {
        try {
            List<ModalidadeEsportivaDto> listaModalidade = modalidadeEsportivaService.listarModalidades();
            return ResponseEntity.status(HttpStatus.OK).body(listaModalidade);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }  catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<Object> buscarModalidade(@PathVariable String nome) {
        try {
            Object modalidade = modalidadeEsportivaService.buscarModalidades(nome);
            return ResponseEntity.status(HttpStatus.OK).body(modalidade);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> desativarModalidade(@PathVariable Long id) {
        try {
            Object modalidadeDesativada = modalidadeEsportivaService.desativarModalidade(id);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeDesativada);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/inscrever/{idAcademico}/{idModalidade}")
    public ResponseEntity<Object> inscreverEmModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.inscreverEmModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/listar/{idAcademico}")
    public ResponseEntity<List<ModalidadeEsportiva>> listarModalidadesAcademico(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportiva> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesInscritas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/buscar/{idAcademico}/modalidade")
    public ResponseEntity<List<ModalidadeEsportiva>> listarModalidadesOutroUsuario(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportiva> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesOutroUsuario(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (ConteudoPrivadoException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("/remover/{idAcademico}/{idModalidade}")
    public ResponseEntity<Void> cancelarInscricaoModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.cancelarInscricaoModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{idModalidade}/estatisticas")
    public ResponseEntity<EstatisticasGeraisModalidadeDto> obterEstatisticasGeraisPorModalidade(@PathVariable Long idModalidade) {
        try {
            EstatisticasGeraisModalidadeDto estatisticas = modalidadeEsportivaService.estatisticasGeraisPorModalidade(idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
