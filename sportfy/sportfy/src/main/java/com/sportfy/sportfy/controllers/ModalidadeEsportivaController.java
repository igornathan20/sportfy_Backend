package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.MetaEsportivaDto;
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
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class ModalidadeEsportivaController {
    @Autowired
    ModalidadeEsportivaService modalidadeEsportivaService;

    @PostMapping()
    public ResponseEntity<ModalidadeEsportivaDto> criarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            ModalidadeEsportivaDto novaModalidade = modalidadeEsportivaService.criarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
        } catch (ModalidadeJaExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    public ResponseEntity<ModalidadeEsportivaDto> editarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            ModalidadeEsportivaDto modalidadeEditada = modalidadeEsportivaService.editarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeEditada);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ModalidadeJaExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @GetMapping("/buscar")
    public ResponseEntity<List<ModalidadeEsportivaDto>> buscarModalidade(@RequestParam String nome) {
        try {
            List<ModalidadeEsportivaDto> modalidade = modalidadeEsportivaService.buscarModalidades(nome);
            return ResponseEntity.status(HttpStatus.OK).body(modalidade);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Object> desativarModalidade(@PathVariable Long id) {
        try {
            Object modalidadeDesativada = modalidadeEsportivaService.desativarModalidade(id);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeDesativada);
        } catch (ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    public ResponseEntity<List<ModalidadeEsportivaDto>> listarModalidadesAcademico(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportivaDto> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesInscritas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException | RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/buscar/{idAcademico}/modalidade")
    public ResponseEntity<List<ModalidadeEsportivaDto>> listarModalidadesOutroUsuario(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportivaDto> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesOutroUsuario(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (ConteudoPrivadoException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @PostMapping("/metaEsportiva")
    public ResponseEntity<MetaEsportivaDto> adicionarMetaEsportiva(@RequestBody MetaEsportivaDto metaEsportivaDto) {
        try {
            MetaEsportivaDto novaMeta = modalidadeEsportivaService.adicionarMetaEsportiva(metaEsportivaDto);
            return ResponseEntity.status(HttpStatus.OK).body(novaMeta);
        } catch (TipoInvalidoException | ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/metaEsportiva/{id}")
    public ResponseEntity<MetaEsportivaDto> atualizarMetaEsportiva(@PathVariable Long id, @RequestBody MetaEsportivaDto metaEsportivaDto) {
        try {
            MetaEsportivaDto metaAtualizada = modalidadeEsportivaService.atualizarMetaEsportiva(metaEsportivaDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(metaAtualizada);
        } catch (RegistroNaoEncontradoException | ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("metaEsportiva/{id}")
    public ResponseEntity<Void> desativarMetaEsportiva(@PathVariable Long id) {
        try {
            modalidadeEsportivaService.desativarMetaEsportiva(id);
            return ResponseEntity.noContent().build();
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/metaEsportiva/listar/{idModalidade}")
    public ResponseEntity<List<MetaEsportivaDto>> listarMetaEsportivaPorModalidade(@PathVariable Long idModalidade) {
        try {
            List<MetaEsportivaDto> metas = modalidadeEsportivaService.listarMetaEsportivaPorModalidade(idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(metas);
        } catch (RegistroNaoEncontradoException | ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
