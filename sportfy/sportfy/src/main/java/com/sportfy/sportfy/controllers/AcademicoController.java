package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.exeptions.*;
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
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequiredArgsConstructor
public class AcademicoController {

    @Autowired
    AcademicoService academicoService;

    @PostMapping("/cadastrar")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<AcademicoResponseDto> cadastrar(@RequestBody @Valid AcademicoDto academico) {
        try {
            AcademicoResponseDto academicoCriado = academicoService.cadastrar(academico);
            return ResponseEntity.status(HttpStatus.CREATED).body(academicoCriado);
        } catch(EmailInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (PermissaoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar/{idAcademico}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<AcademicoResponseDto> atualizar(@PathVariable("idAcademico") Long idAcademico, @RequestBody AcademicoDto academico) {
        try {
            AcademicoResponseDto academicoAtualizado = academicoService.atualizar(idAcademico, academico);
            return ResponseEntity.status(HttpStatus.OK).body(academicoAtualizado);
        } catch (EmailInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/inativar/{idAcademico}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<AcademicoResponseDto> inativar(@PathVariable("idAcademico") Long idAcademico) {
        try {
            AcademicoResponseDto academicoInativado = academicoService.inativar(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(academicoInativado);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultar/{idUsuario}")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<AcademicoResponseDto> consultar(@PathVariable("idUsuario") Long idUsuario) {
        try {
            AcademicoResponseDto academicoConsultado = academicoService.consultar(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(academicoConsultado);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    //@PreAuthorize("hasRole('ROLE_ACADEMICO')")
    public ResponseEntity<List<AcademicoResponseDto>> listar() {
        try {
            List<AcademicoResponseDto> listaAcademico = academicoService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(listaAcademico);
        } catch (ListaAcademicosVaziaException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/notificacoes/{idAcademico}/{tipoNotificacao}")
    public ResponseEntity<Boolean> retornaPreferenciaNotificacao(@PathVariable Long idAcademico, @PathVariable String tipoNotificacao){
        try {
            boolean notificar = academicoService.retornaNotificacao(idAcademico, tipoNotificacao);
            return ResponseEntity.status(HttpStatus.OK).body(notificar);
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/notificacoes/{idAcademico}")
    public ResponseEntity<NotificacaoDto> retornaTodasPreferenciaNotificacao(@PathVariable Long idAcademico){
        try {
            NotificacaoDto notificacoes = academicoService.retornaTodasNotificacoes(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(notificacoes);
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping ("/notificacoes")
    public ResponseEntity<NotificacaoDto> alteraNotificacao(@RequestBody NotificacaoDto userNotificacao){
        try {
            Notificacao notificacao = academicoService.alteraNotificacao(userNotificacao);
            return ResponseEntity.status(HttpStatus.OK).body(NotificacaoDto.toEntity(notificacao));
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/privacidade/{idAcademico}/{tipoPrivacidade}")
    public ResponseEntity<Boolean> retornaPrivacidade(@PathVariable Long idAcademico, @PathVariable String tipoPrivacidade){
        try {
            boolean privacidade = academicoService.retornaPrivacidade(idAcademico, tipoPrivacidade);
            return ResponseEntity.status(HttpStatus.OK).body(privacidade);
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/privacidade/{idAcademico}")
    public ResponseEntity<PrivacidadeDto> retornaTodasPrivacidade(@PathVariable Long idAcademico){
        try {
            PrivacidadeDto privacidade = academicoService.retornaTodasPrivacidade(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(privacidade);
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping ("/privacidade")
    public ResponseEntity<PrivacidadeDto> alteraPrivacidade(@RequestBody PrivacidadeDto userPrivacidade){
        try {
            Privacidade privacidade = academicoService.alteraPrivacidade(userPrivacidade);
            return ResponseEntity.status(HttpStatus.OK).body(PrivacidadeDto.fromPrivacidade(privacidade));
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/estatisticas/{idAcademico}/modalidade/{idModalidade}")
    public ResponseEntity<EstatisticasPessoaisModalidadeDto> obterEstatisticasPessoaisPorModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            EstatisticasPessoaisModalidadeDto estatisticas = academicoService.estatisticasPessoaisPorModalidade(idAcademico, idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
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

    @GetMapping("/buscar/estatisticas/{idAcademico}/modalidade/{idModalidade}")
    public ResponseEntity<EstatisticasPessoaisModalidadeDto> obterEstatisticasOutroUsuarioPorModalidade(@PathVariable Long idAcademico, @PathVariable Long idModalidade) {
        try {
            EstatisticasPessoaisModalidadeDto estatisticas = academicoService.estatisticasPessoaisPorModalidade(idAcademico, idModalidade);
            return ResponseEntity.ok(estatisticas);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
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

    @GetMapping("/uso/{idAcademico}")
    public ResponseEntity<EstatisticasDeUsoDto> getEstatisticasDeUso(@PathVariable Long idAcademico) {
        try {
            EstatisticasDeUsoDto estatisticasDeUso = academicoService.estatisticasDeUso(idAcademico);
            return ResponseEntity.ok(estatisticasDeUso);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException | RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
