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
import com.sportfy.sportfy.dtos.PublicacaoDto;
import com.sportfy.sportfy.exeptions.PublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioNaoExisteException;
import com.sportfy.sportfy.services.PublicacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicacao")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class PublicacaoController {

    private static final Logger logger = LoggerFactory.getLogger(PublicacaoController.class);
    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping("/cadastrarPublicacao")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> cadastrarPublicacao(@RequestBody @Valid PublicacaoDto publicacao) {
        try {
            Object publicacaoCriada = publicacaoService.cadastrarPublicacao(publicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizarPublicacao/{idPublicacao}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> atualizarPublicacao(@PathVariable("idPublicacao") Long idPublicacao, @RequestBody @Valid PublicacaoDto publicacao) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            Object publicacaoAtualizada = publicacaoService.atualizarPublicacao(idPublicacao, publicacao, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(publicacaoAtualizada);
        } catch (PublicacaoNaoExisteException e) {
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

    @DeleteMapping("/removerPublicacao/{idPublicacao}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> removerPublicacao(@PathVariable("idPublicacao") Long idPublicacao) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            Object publicacaoRemovida = publicacaoService.removerPublicacao(idPublicacao, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(publicacaoRemovida);
        } catch (PublicacaoNaoExisteException e) {
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

    @PostMapping("/curtirPublicacao/{idUsuario}/{idPublicacao}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> curtirPublicacao(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object publicacaoCurtida = publicacaoService.curtirPublicacao(idUsuario, idPublicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCurtida);
        } catch (UsuarioCurtidaPublicacaoJaExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }  catch (PublicacaoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerCurtidaPublicacao/{idUsuario}/{idPublicacao}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Object> removerCurtidaPublicacao(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object isCurtidaPublicacaoRemovida = publicacaoService.removerCurtidaPublicacao(idUsuario, idPublicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(isCurtidaPublicacaoRemovida);
        } catch (UsuarioCurtidaPublicacaoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }  catch (PublicacaoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCanal}/publicacoes")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<PublicacaoDto>> listarPublicacoesPorCanal(@PathVariable Long idCanal, Pageable pageable) {
        try {
            Page<PublicacaoDto> publicacoes = publicacaoService.listarPublicacoesPorCanal(idCanal, pageable);
            return ResponseEntity.ok(publicacoes);
        } catch (PublicacaoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCanal}/buscar-publicacoes-username/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<PublicacaoDto>> listarPublicacoesPorUsuario(@PathVariable Long idCanal, @PathVariable String username, Pageable pageable) {
        try {
            Page<PublicacaoDto> publicacoes = publicacaoService.listarPublicacoesPorUsuario(idCanal, username, pageable);
            return ResponseEntity.ok(publicacoes);
        } catch (UsuarioNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (PublicacaoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
