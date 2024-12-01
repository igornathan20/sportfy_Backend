package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.Curso;
import com.sportfy.sportfy.models.Privacidade;
import com.sportfy.sportfy.repositories.CursoRepository;
import com.sportfy.sportfy.services.AcademicoService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(value = "/academico")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequiredArgsConstructor
public class AcademicoController {
    private static final Logger logger = LoggerFactory.getLogger(AcademicoController.class);
    @Autowired
    AcademicoService academicoService;
    @Autowired
    CursoRepository cursoRepository;

    @PostMapping("/cadastrar")
    @PermitAll
    public ResponseEntity<AcademicoResponseDto> cadastrar(@RequestBody @Valid AcademicoDto academico) {
        try {
            AcademicoResponseDto academicoCriado = academicoService.cadastrar(academico);
            return ResponseEntity.status(HttpStatus.CREATED).body(academicoCriado);
        } catch(EmailInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AcademicoResponseDto> atualizar(@PathVariable("idAcademico") Long idAcademico, @RequestBody AcademicoDto academico) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            AcademicoResponseDto academicoAtualizado = academicoService.atualizar(idAcademico, academico, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(academicoAtualizado);
        } catch (EmailInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/inativar/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AcademicoResponseDto> inativar(@PathVariable("idAcademico") Long idAcademico) {
        try {
            AcademicoResponseDto academicoInativado = academicoService.inativar(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(academicoInativado);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultar/{idUsuario}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AcademicoResponseDto> consultar(@PathVariable("idUsuario") Long idUsuario) {
        try {
            AcademicoResponseDto academicoConsultado = academicoService.consultar(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(academicoConsultado);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{userName}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AcademicoResponseDto> buscarPorUsername(@PathVariable String userName) {
        try {
            AcademicoResponseDto academicoConsultado = academicoService.buscarPorUsername(userName);
            return ResponseEntity.status(HttpStatus.OK).body(academicoConsultado);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<AcademicoResponseDto>> listar(Pageable pageable) {
        try {
            Page<AcademicoResponseDto> listaAcademico = academicoService.listar(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(listaAcademico);
        } catch (ListaAcademicosVaziaException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/privacidade/{idAcademico}/{tipoPrivacidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Boolean> retornaPrivacidade(@PathVariable Long idAcademico, @PathVariable String tipoPrivacidade){
        try {
            boolean privacidade = academicoService.retornaPrivacidade(idAcademico, tipoPrivacidade);
            return ResponseEntity.status(HttpStatus.OK).body(privacidade);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/privacidade/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<PrivacidadeDto> retornaTodasPrivacidade(@PathVariable Long idAcademico){
        try {
            PrivacidadeDto privacidade = academicoService.retornaTodasPrivacidade(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(privacidade);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping ("/privacidade")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<PrivacidadeDto> alteraPrivacidade(@RequestBody PrivacidadeDto userPrivacidade){
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            Privacidade privacidade = academicoService.alteraPrivacidade(userPrivacidade, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(PrivacidadeDto.fromPrivacidade(privacidade));
        }catch (AccessDeniedException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/estatisticas/{idAcademico}/modalidade/{idModalidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<EstatisticasPessoaisModalidadeDto> obterEstatisticasPessoaisPorModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            EstatisticasPessoaisModalidadeDto estatisticas = academicoService.estatisticasPessoaisPorModalidade(idAcademico, idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
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

    @GetMapping("/buscar/estatisticas/{idAcademico}/modalidade/{idModalidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<EstatisticasPessoaisModalidadeDto> obterEstatisticasOutroUsuarioPorModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            EstatisticasPessoaisModalidadeDto estatisticas = academicoService.estatisticasOutroUsuarioPorModalidade(idAcademico, idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ConteudoPrivadoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/uso/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<EstatisticasPessoaisDto> getEstatisticasDeUso(@PathVariable Long idAcademico) {
        try {
            EstatisticasPessoaisDto estatisticasDeUso = academicoService.estatisticasDeUso(idAcademico);
            return ResponseEntity.ok(estatisticasDeUso);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException | RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cursos/ufpr")
    @PermitAll
    public List<String> listarCursosUfpr() {
        return cursoRepository.findAll().stream()
                .map(Curso::getNome)
                .toList();
    }
}
