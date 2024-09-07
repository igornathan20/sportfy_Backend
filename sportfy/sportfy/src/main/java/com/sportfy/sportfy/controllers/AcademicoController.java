package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.AcademicoDto;
import com.sportfy.sportfy.dtos.NotificacaoDto;
import com.sportfy.sportfy.dtos.PrivacidadeDto;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.Academico;
import com.sportfy.sportfy.models.Notificacao;
import com.sportfy.sportfy.models.Privacidade;
import com.sportfy.sportfy.services.AcademicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/academico")
@RequiredArgsConstructor
public class AcademicoController {

    @Autowired
    AcademicoService academicoService;

    @PostMapping("/cadastrar")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid AcademicoDto academico) {
        try {
            Object academicoCriado = academicoService.cadastrar(academico);
            return ResponseEntity.status(HttpStatus.CREATED).body(academicoCriado);
        } catch(EmailInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (OutroUsuarioComDadosJaExistentes e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (PermissaoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar/{idAcademico}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<Object> atualizar(@PathVariable("idAcademico") Long idAcademico, @RequestBody @Valid AcademicoDto academico) {
        try {
            Object academicoAtualizado = academicoService.atualizar(idAcademico, academico);
            return ResponseEntity.status(HttpStatus.OK).body(academicoAtualizado);
        } catch (EmailInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OutroUsuarioComDadosJaExistentes e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/inativar/{idAcademico}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<Object> inativar(@PathVariable("idAcademico") Long idAcademico) {
        try {
            Object academicoInativado = academicoService.inativar(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(academicoInativado);
        } catch (AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultar/{idUsuario}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<Object> consultar(@PathVariable("idUsuario") Long idUsuario) {
        try {
            Object academicoConsultado = academicoService.consultar(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(academicoConsultado);
        } catch (AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<?> listar() {
        try {
            List<AcademicoDto> listaAcademico = academicoService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(listaAcademico);
        } catch (ListaAcademicosVaziaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
/*
    @GetMapping("/buscarUsuario")
    public ResponseEntity<Object> consultar(@PathVariable("idUsuario") Long idUsuario) {
        try {
            Object academicoConsultado = academicoService.consultar(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(academicoConsultado);
        } catch (AcademicoNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/

    @GetMapping("/notificacoes/{idAcademico}/{tipoNotificacao}")
    public ResponseEntity<Boolean> retornaPreferenciaNotificacao(@PathVariable Long idAcademico, @PathVariable String tipoNotificacao){
        try {
            boolean notificar = academicoService.retornaNotificacao(idAcademico, tipoNotificacao);
            return ResponseEntity.status(HttpStatus.OK).body(notificar);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping ("/notificacao")
    public ResponseEntity<NotificacaoDto> alteraNotificacao(@RequestBody NotificacaoDto userNotificacao){
        try {
            Notificacao notificacao = academicoService.alteraNotificacao(userNotificacao);
            return ResponseEntity.status(HttpStatus.OK).body(NotificacaoDto.fromNotificacao(notificacao));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/privacidade/{idAcademico}/{tipoPrivacidade}")
    public ResponseEntity<Boolean> retornaPrivacidade(@PathVariable Long idAcademico, @PathVariable String tipoPrivacidade){
        try {
            boolean privacidade = academicoService.retornaPrivacidade(idAcademico, tipoPrivacidade);
            return ResponseEntity.status(HttpStatus.OK).body(privacidade);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping ("/privacidade")
    public ResponseEntity<PrivacidadeDto> alteraPrivacidade(@RequestBody PrivacidadeDto userPrivacidade){
        try {
            Privacidade privacidade = academicoService.alteraPrivacidade(userPrivacidade);
            return ResponseEntity.status(HttpStatus.OK).body(PrivacidadeDto.fromPrivacidade(privacidade));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
