package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportfy.sportfy.dtos.PublicacaoDto;
import com.sportfy.sportfy.exeptions.PublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoNaoExisteException;
import com.sportfy.sportfy.services.PublicacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicacao")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping("/cadastrarPublicacao")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR')
    public ResponseEntity<Object> cadastrarPublicacao(@RequestBody @Valid PublicacaoDto publicacao) {
        try {
            Object publicacaoCriada = publicacaoService.cadastrarPublicacao(publicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizarPublicacao/{idPublicacao}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR')
    public ResponseEntity<Object> atualizarPublicacao(@PathVariable("idPublicacao") Long idPublicacao, @RequestBody @Valid PublicacaoDto publicacao) {
        try {
            Object publicacaoAtualizada = publicacaoService.atualizarPublicacao(idPublicacao, publicacao);
            return ResponseEntity.status(HttpStatus.OK).body(publicacaoAtualizada);
        } catch (PublicacaoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerPublicacao/{idPublicacao}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR')
    public ResponseEntity<Object> removerPublicacao(@PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object publicacaoRemovida = publicacaoService.removerPublicacao(idPublicacao);
            return ResponseEntity.status(HttpStatus.OK).body(publicacaoRemovida);
        } catch (PublicacaoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/curtirPublicacao/{idUsuario}/{idPublicacao}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR')
    public ResponseEntity<Object> curtirPublicacao(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object publicacaoCurtida = publicacaoService.curtirPublicacao(idUsuario, idPublicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCurtida);
        } catch (UsuarioCurtidaPublicacaoJaExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }  catch (PublicacaoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerCurtidaPublicacao/{idUsuario}/{idPublicacao}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR')
    public ResponseEntity<Object> removerCurtidaPublicacao(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object isCurtidaPublicacaoRemovida = publicacaoService.removerCurtidaPublicacao(idUsuario, idPublicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(isCurtidaPublicacaoRemovida);
        } catch (UsuarioCurtidaPublicacaoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }  catch (PublicacaoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCanal}/publicacoes")
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
}
