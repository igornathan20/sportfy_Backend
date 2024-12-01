package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.EstatisticasGeraisModalidadeDto;
import com.sportfy.sportfy.dtos.MetaEsportivaDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.services.ModalidadeEsportivaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private static final Logger logger = LoggerFactory.getLogger(ModalidadeEsportivaController.class);
    @Autowired
    ModalidadeEsportivaService modalidadeEsportivaService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ModalidadeEsportivaDto> criarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            ModalidadeEsportivaDto novaModalidade = modalidadeEsportivaService.criarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
        } catch (ModalidadeJaExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<ModalidadeEsportivaDto> editarModalidade(@RequestBody ModalidadeEsportivaDto modalidade) {
        try {
            ModalidadeEsportivaDto modalidadeEditada = modalidadeEsportivaService.editarModalidade(modalidade);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeEditada);
        } catch (ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ModalidadeJaExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ModalidadeEsportivaDto>> listarModalidades() {
        try {
            List<ModalidadeEsportivaDto> listaModalidade = modalidadeEsportivaService.listarModalidades();
            return ResponseEntity.status(HttpStatus.OK).body(listaModalidade);
        } catch (ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }  catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ModalidadeEsportivaDto>> buscarModalidade(@RequestParam String nome) {
        try {
            List<ModalidadeEsportivaDto> modalidade = modalidadeEsportivaService.buscarModalidades(nome);
            return ResponseEntity.status(HttpStatus.OK).body(modalidade);
        } catch (ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> desativarModalidade(@PathVariable Long id) {
        try {
            Object modalidadeDesativada = modalidadeEsportivaService.desativarModalidade(id);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeDesativada);
        } catch (ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/inscrever/{idAcademico}/{idModalidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> inscreverEmModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.inscreverEmModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException | InscricaoEmModalidadeNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/listar/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ModalidadeEsportivaDto>> listarModalidadesAcademico(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportivaDto> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesInscritas(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException | RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{idAcademico}/modalidade")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<ModalidadeEsportivaDto>> listarModalidadesOutroUsuario(@PathVariable Long idAcademico) {
        try {
            List<ModalidadeEsportivaDto> modalidadeAcademico = modalidadeEsportivaService.listarModalidadesOutroUsuario(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(modalidadeAcademico);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (ConteudoPrivadoException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remover/{idAcademico}/{idModalidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> cancelarInscricaoModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            modalidadeEsportivaService.cancelarInscricaoModalidade(idAcademico, idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (ModalidadeNaoExistenteException | AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idModalidade}/estatisticas")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<EstatisticasGeraisModalidadeDto> obterEstatisticasGeraisPorModalidade(@PathVariable Long idModalidade) {
        try {
            EstatisticasGeraisModalidadeDto estatisticas = modalidadeEsportivaService.estatisticasGeraisPorModalidade(idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/metaEsportiva")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<MetaEsportivaDto> adicionarMetaEsportiva(@RequestBody MetaEsportivaDto metaEsportivaDto) {
        try {
            MetaEsportivaDto novaMeta = modalidadeEsportivaService.adicionarMetaEsportiva(metaEsportivaDto);
            return ResponseEntity.status(HttpStatus.OK).body(novaMeta);
        } catch (TipoInvalidoException | ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/metaEsportiva/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<MetaEsportivaDto> atualizarMetaEsportiva(@PathVariable Long id, @RequestBody MetaEsportivaDto metaEsportivaDto) {
        try {
            MetaEsportivaDto metaAtualizada = modalidadeEsportivaService.atualizarMetaEsportiva(metaEsportivaDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(metaAtualizada);
        } catch (RegistroNaoEncontradoException | ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("metaEsportiva/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> desativarMetaEsportiva(@PathVariable Long id) {
        try {
            modalidadeEsportivaService.desativarMetaEsportiva(id);
            return ResponseEntity.noContent().build();
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/metaEsportiva/listar/{idModalidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<MetaEsportivaDto>> listarMetaEsportivaPorModalidade(@PathVariable Long idModalidade) {
        try {
            List<MetaEsportivaDto> metas = modalidadeEsportivaService.listarMetaEsportivaPorModalidade(idModalidade);
            return ResponseEntity.status(HttpStatus.OK).body(metas);
        } catch (RegistroNaoEncontradoException | ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
