package com.sportfy.sportfy.controllers;

import java.nio.file.AccessDeniedException;

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

import com.sportfy.sportfy.dtos.ComentarioDto;
import com.sportfy.sportfy.exeptions.ComentarioNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaComentarioJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaComentarioNaoExisteException;
import com.sportfy.sportfy.services.ComentarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentario")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class ComentarioController {

    private static final Logger logger = LoggerFactory.getLogger(ComentarioController.class);
    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/cadastrarComentario")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> cadastrarComentario(@RequestBody @Valid ComentarioDto comentario) {
        try {
            Object comentarioCriada = comentarioService.cadastrarComentario(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriada);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizarComentario/{idComentario}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> atualizarComentario(@PathVariable("idComentario") Long idComentario, @RequestBody @Valid ComentarioDto comentario) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            Object comentarioAtualizado = comentarioService.atualizarComentario(idComentario, comentario, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(comentarioAtualizado);
        } catch (ComentarioNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerComentario/{idComentario}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> removerComentario(@PathVariable("idComentario") Long idComentario) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            Object comentarioRemovido = comentarioService.removerComentario(idComentario, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(comentarioRemovido);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (ComentarioNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/curtirComentario/{idUsuario}/{idComentario}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> curtirComentario(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idComentario") Long idComentario) {
        try {
            Object comentarioCurtida = comentarioService.curtirComentario(idUsuario, idComentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCurtida);
        } catch (UsuarioCurtidaComentarioJaExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }  catch (ComentarioNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerCurtidaComentario/{idUsuario}/{idComentario}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> removerCurtidaComentario(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idComentario") Long idComentario) {
        try {
            Object isCurtidaComentarioRemovida = comentarioService.removerCurtidaComentario(idUsuario, idComentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(isCurtidaComentarioRemovida);
        } catch (UsuarioCurtidaComentarioNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }  catch (ComentarioNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idPublicacao}/comentarios")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<ComentarioDto>> listarComentariosPorPublicacao(@PathVariable Long idPublicacao, Pageable pageable) {
        try {
            Page<ComentarioDto> comentarios = comentarioService.listarComentariosPorPublicacao(idPublicacao, pageable);
            return ResponseEntity.ok(comentarios);
        } catch (ComentarioNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
