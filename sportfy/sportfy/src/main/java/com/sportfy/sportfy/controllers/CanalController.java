package com.sportfy.sportfy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportfy.sportfy.dtos.CanalDto;
import com.sportfy.sportfy.dtos.ComentarioDto;
import com.sportfy.sportfy.dtos.PublicacaoDto;
import com.sportfy.sportfy.exeptions.ComentarioNaoExisteException;
import com.sportfy.sportfy.exeptions.ListaCanalVazioException;
import com.sportfy.sportfy.exeptions.PublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaComentarioJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaComentarioNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoNaoExisteException;
import com.sportfy.sportfy.services.CanalService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/canal")
public class CanalController {
    @Autowired
    private CanalService canalService;

    @PostMapping("/cadastrarPublicacao")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> cadastrarPublicacao(@RequestBody @Valid PublicacaoDto publicacao) {
        try {
            Object publicacaoCriada = canalService.cadastrarPublicacao(publicacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoCriada);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizarPublicacao/{idPublicacao}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> atualizarPublicacao(@PathVariable("idPublicacao") Long idPublicacao, @RequestBody @Valid PublicacaoDto publicacao) {
        try {
            Object publicacaoAtualizada = canalService.atualizarPublicacao(idPublicacao, publicacao);
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
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> removerPublicacao(@PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object publicacaoRemovida = canalService.removerPublicacao(idPublicacao);
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
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> curtirPublicacao(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object publicacaoCurtida = canalService.curtirPublicacao(idUsuario, idPublicacao);
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
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> removerCurtidaPublicacao(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idPublicacao") Long idPublicacao) {
        try {
            Object isCurtidaPublicacaoRemovida = canalService.removerCurtidaPublicacao(idUsuario, idPublicacao);
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

    @PostMapping("/cadastrarComentario")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> cadastrarComentario(@RequestBody @Valid ComentarioDto comentario) {
        try {
            Object comentarioCriada = canalService.cadastrarComentario(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCriada);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizarComentario/{idComentario}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> atualizarComentario(@PathVariable("idComentario") Long idComentario, @RequestBody @Valid ComentarioDto comentario) {
        try {
            Object comentarioAtualizado = canalService.atualizarComentario(idComentario, comentario);
            return ResponseEntity.status(HttpStatus.OK).body(comentarioAtualizado);
        } catch (ComentarioNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerComentario/{idComentario}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> removerComentario(@PathVariable("idComentario") Long idComentario) {
        try {
            Object comentarioRemovido = canalService.removerComentario(idComentario);
            return ResponseEntity.status(HttpStatus.OK).body(comentarioRemovido);
        } catch (ComentarioNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/curtirComentario/{idUsuario}/{idComentario}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> curtirComentario(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idComentario") Long idComentario) {
        try {
            Object comentarioCurtida = canalService.curtirComentario(idUsuario, idComentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCurtida);
        } catch (UsuarioCurtidaComentarioJaExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }  catch (ComentarioNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/removerCurtidaComentario/{idUsuario}/{idComentario}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<Object> removerCurtidaComentario(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idComentario") Long idComentario) {
        try {
            Object isCurtidaComentarioRemovida = canalService.removerCurtidaComentario(idUsuario, idComentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(isCurtidaComentarioRemovida);
        } catch (UsuarioCurtidaComentarioNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }  catch (ComentarioNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_ADMINISTRADOR_MASTER')")
    public ResponseEntity<?> listar() {
        try {
            List<CanalDto> listaCanal = canalService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(listaCanal);
        } catch (ListaCanalVazioException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
