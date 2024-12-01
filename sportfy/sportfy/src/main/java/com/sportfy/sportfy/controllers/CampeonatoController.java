package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.services.CampeonatoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/campeonatos")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class CampeonatoController {

    private static final Logger logger = LoggerFactory.getLogger(CampeonatoController.class);
    @Autowired
    private CampeonatoService campeonatoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<CampeonatoResponseDto> criarCampeonato(@RequestBody CampeonatoDto campeonatoDto) {
        try {
            CampeonatoResponseDto campeonato = campeonatoService.criarCampeonato(campeonatoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(campeonato);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (CampeonatoInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{idCampeonato}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<CampeonatoResponseDto> editarCampeonato(@PathVariable Long idCampeonato, @RequestBody CampeonatoDto campeonatoDto) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            CampeonatoResponseDto campeonato = campeonatoService.editarCampeonato(idCampeonato, campeonatoDto, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(campeonato);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (TipoInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<CampeonatoResponseDto>> listarTodosCampeonatos(Pageable page) {
        try {
            Page<CampeonatoResponseDto> campeonatos = campeonatoService.listarTodosCampeonatos(page);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filtrar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<CampeonatoResponseDto>> listarCampeonatosComFiltro(
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String aposta,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataCriacao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @RequestParam(required = false) Integer limiteTimes,
            @RequestParam(required = false) Integer limiteParticipantes,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) String privacidadeCampeonato,
            @RequestParam(required = false) Long idAcademico,
            @RequestParam(required = false) Long idModalidadeEsportiva,
            @RequestParam(required = false) String situacaoCampeonato) {
        try {
            CampeonatoDto campeonatoDto = new CampeonatoDto(
                    null, codigo, null, titulo, descricao, aposta, dataCriacao, dataInicio, dataFim,
                    limiteTimes != null ? limiteTimes : 0,
                    limiteParticipantes != null ? limiteParticipantes : 0,
                    ativo != null ? ativo : false,
                    null, privacidadeCampeonato != null ? privacidadeCampeonato : "PUBLICO",
                    idAcademico, idModalidadeEsportiva,
                    situacaoCampeonato != null ? situacaoCampeonato : "EM_ABERTO"
            );

            List<CampeonatoResponseDto> campeonatos = campeonatoService.listarCampeonatosComFiltro(campeonatoDto);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/listar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<CampeonatoResponseDto>> listarCampeonatosPorModalidadeInscrita(@PathVariable Long idAcademico, Pageable pageable) {
        try {
            Page<CampeonatoResponseDto> campeonatos = campeonatoService.listarCampeonatosPorModalidadesInscritas(idAcademico, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException | AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/inscritos")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<CampeonatoResponseDto>> listarCampeonatosInscritos(@PathVariable Long idAcademico, Pageable pageable) {
        try {
            Page<CampeonatoResponseDto> campeonatos = campeonatoService.listarCampeonatosInscritos(idAcademico, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException | AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/meusCampeonatos")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<CampeonatoResponseDto>> listarCampeonatosCriados(@PathVariable Long idAcademico, Pageable pageable) {
        try {
            Page<CampeonatoResponseDto> campeonatos = campeonatoService.listarCampeonatosCriados(idAcademico, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException | AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> excluirCampeonato(@PathVariable Long id) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            campeonatoService.excluirCampeonato(id, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<CampeonatoResponseDto> desativarCampeonato(@PathVariable Long id) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            CampeonatoResponseDto campeonato = campeonatoService.desativarCampeonato(id, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(campeonato);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/times")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<TimeDto> criarTime(@RequestBody TimeDto novoTime) {
        try {
            TimeDto timeCriado = campeonatoService.criarTime(novoTime);
            return ResponseEntity.status(HttpStatus.CREATED).body(timeCriado);
        } catch (CampeonatoInvalidoException | TimeInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PasswordInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCampeonato}/times")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<TimeDto>> listarTimesCampeonato(@PathVariable Long idCampeonato) {
        try {
            List<TimeDto> campeonatos = campeonatoService.listarTimesCampeonato(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException  e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/times/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<JogadorDto> adicionarJogadorTime(@RequestBody TimeDto timeDto, @PathVariable Long idAcademico) {
        try {
            JogadorDto jogador = campeonatoService.adicionarJogadorTime(timeDto, idAcademico);
            return ResponseEntity.status(HttpStatus.CREATED).body(jogador);
        } catch (CampeonatoInvalidoException | TimeInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PasswordInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (OutroUsuarioComDadosJaExistentes e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idCampeonato}/times/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<TimeDto> criarTimeComUmJogador(@PathVariable Long idCampeonato, @PathVariable Long idAcademico, @RequestBody String senhaCampeonato) {
        try {
            TimeDto timeCriado = campeonatoService.criarTimeComUmJogador(idCampeonato, idAcademico, senhaCampeonato);
            return ResponseEntity.status(HttpStatus.CREATED).body(timeCriado);
        } catch (CampeonatoInvalidoException | TimeInvalidoException | RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PasswordInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("{idCampeonato}/times/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> removerJogadorTime(@PathVariable Long idCampeonato, @PathVariable Long idAcademico) {
        try {
            campeonatoService.removerJogadorTime(idCampeonato, idAcademico);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CampeonatoInvalidoException  e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idCampeonato}/jogadores")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<JogadorDto>> listarJogadoresPorCampeonato(@PathVariable Long idCampeonato, Pageable pageable) {
        try {
            Page<JogadorDto> jogadores = campeonatoService.listarJogadoresCampeonato(idCampeonato, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(jogadores);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/jogadores-enfrentados")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<AcademicoDto>> listarJogadoresEnfrentados(@PathVariable Long idAcademico, Pageable pageable) {
        try {
            Page<AcademicoDto> jogadoresEnfrentados = campeonatoService.listarJogadoresEnfrentados(idAcademico, pageable);
            return ResponseEntity.ok(jogadoresEnfrentados);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/situacao")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<JogadorDto> mudarSituacaoJogador(@PathVariable Long id, @RequestParam String situacao) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            JogadorDto jogadorAtualizado = campeonatoService.mudarSituacaoJogador(id, situacao, usuarioAutenticado);
            return ResponseEntity.ok(jogadorAtualizado);
        } catch (TipoInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{idCampeonato}/primeira-fase")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<PartidaDto>> definirPrimeiraFase(@PathVariable Long idCampeonato) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            List<PartidaDto> partidas = campeonatoService.definirPrimeiraFase(idCampeonato, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(partidas);
        } catch (RegistroNaoEncontradoException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (CampeonatoInvalidoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCampeonato}/partidas")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<PartidaDto>> listarPartidas(@PathVariable Long idCampeonato) {
        try {
            List<PartidaDto> partidas = campeonatoService.listarPartidas(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(partidas);
        } catch (RegistroNaoEncontradoException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idCampeonato}/avancar-fase")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<List<PartidaDto>> avancarDeFase(@PathVariable Long idCampeonato) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            List<PartidaDto> partidas = campeonatoService.avancarDeFase(idCampeonato, usuarioAutenticado);
            return ResponseEntity.status(HttpStatus.OK).body(partidas);
        }catch (RegistroNaoEncontradoException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (AvancarFaseException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch(Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/partidas/{idPartida}/pontuacao")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<PartidaDto> alterarPontuacaoPartida(@PathVariable Long idPartida, @RequestParam int pontuacaoTime1, @RequestParam int pontuacaoTime2) {
        try {
            PartidaDto partida = campeonatoService.alterarPontuacaoPartida(idPartida, pontuacaoTime1, pontuacaoTime2);
            return ResponseEntity.status(HttpStatus.OK).body(partida);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/avaliar")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AvaliacaoJogadorDto> avaliarJogador(@RequestParam Long idAvaliador, @RequestParam Long idAcademico, @RequestParam Long idModalidade, @RequestParam int nota) {
        try {
            AvaliacaoJogadorDto avaliacao = campeonatoService.avaliarJogador(idAvaliador, idAcademico, idModalidade, nota);
            return ResponseEntity.ok(avaliacao);
        } catch (AcademicoNaoExisteException | RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/avaliacao/{idAcademico}/modalidade/{idModalidade}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<AvaliacaoResponseDto> recuperaAvaliacaoPorModalidade(@PathVariable Long idModalidade, @PathVariable Long idAcademico) {
        try {
            AvaliacaoResponseDto avaliacaoResponse = campeonatoService.recuperaAvaliacaoPorModalidade(idModalidade, idAcademico);
            return ResponseEntity.ok(avaliacaoResponse);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/avaliacao/{idAcademico}/mediaAvaliacao")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<MediaAvaliacaoDto> recuperaMediaAvaliacao(@PathVariable Long idAcademico) {
        try {
            MediaAvaliacaoDto avaliacaoResponse = campeonatoService.recuperaMediaAvaliacoes(idAcademico);
            return ResponseEntity.ok(avaliacaoResponse);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/historico/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<CampeonatoResponseDto>> buscarHistoricoCampeonato( @PathVariable Long idAcademico, Pageable pageable) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var usuarioAutenticado = ((UserDetails) authentication.getPrincipal()).getUsername();
            Page<CampeonatoResponseDto> campeonatos = campeonatoService.buscarHistoricoCampeonato(idAcademico, pageable, usuarioAutenticado);
            return ResponseEntity.ok(campeonatos);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/historico/academico/{idAcademico}")
    @PreAuthorize("hasAnyRole('ROLE_ACADEMICO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<CampeonatoResponseDto>> buscarHistoricoCampeonatoOutrosUsuarios( @PathVariable Long idAcademico, Pageable pageable) {
        try {
            Page<CampeonatoResponseDto> campeonatos = campeonatoService.buscarHistoricoCampeonatoOutrosUsuarios(idAcademico, pageable);
            return ResponseEntity.ok(campeonatos);
        } catch (AcademicoNaoExisteException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ConteudoPrivadoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }catch (RegistroNaoEncontradoException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

