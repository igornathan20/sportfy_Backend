package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.*;
import com.sportfy.sportfy.exeptions.*;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.services.CampeonatoService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campeonatos")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8081", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class CampeonatoController {
    @Autowired
    private CampeonatoService campeonatoService;

    @PostMapping
    @PermitAll
    public ResponseEntity<CampeonatoResponseDto> criarCampeonato(@RequestBody CampeonatoDto campeonatoDto) {
        try {
            CampeonatoResponseDto campeonato = campeonatoService.criarCampeonato(campeonatoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(campeonato);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (CampeonatoInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{idCampeonato}")
    @PermitAll
    public ResponseEntity<CampeonatoResponseDto> editarCampeonato(@PathVariable Long idCampeonato, @RequestBody CampeonatoDto campeonatoDto) {
        try {
            CampeonatoResponseDto campeonato = campeonatoService.editarCampeonato(idCampeonato, campeonatoDto);
            return ResponseEntity.status(HttpStatus.OK).body(campeonato);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listar")
    @PermitAll
    public ResponseEntity<List<CampeonatoResponseDto>> listarTodosCampeonatos() {
        try {
            List<CampeonatoResponseDto> campeonatos = campeonatoService.listarTodosCampeonatos();
            return new ResponseEntity<>(campeonatos, HttpStatus.OK);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filtrar")
    @PermitAll
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
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/listar")
    @PermitAll
    public ResponseEntity<List<CampeonatoResponseDto>> listarCampeonatosInscritos(@PathVariable Long idAcademico) {
        try {
            List<CampeonatoResponseDto> campeonatos = campeonatoService.listarCampeonatosInscritos(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException | AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/meusCampeonatos")
    @PermitAll
    public ResponseEntity<List<CampeonatoResponseDto>> listarCampeonatosCriados(@PathVariable Long idAcademico) {
        try {
            List<CampeonatoResponseDto> campeonatos = campeonatoService.listarCampeonatosCriados(idAcademico);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException | AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PermitAll
    public ResponseEntity<Void> excluirCampeonato(@PathVariable Long id) {
        try {
            Optional<Campeonato> campeonato = campeonatoService.excluirCampeonato(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/desativar/{id}")
    @PermitAll
    public ResponseEntity<CampeonatoResponseDto> desativarCampeonato(@PathVariable Long id) {
        try {
            CampeonatoResponseDto campeonato = campeonatoService.desativarCampeonato(id);
            return ResponseEntity.status(HttpStatus.OK).body(campeonato);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/times")
    @PermitAll
    public ResponseEntity<TimeDto> criarTime(@RequestBody TimeDto novoTime) {
        try {
            TimeDto timeCriado = campeonatoService.criarTime(novoTime);
            return ResponseEntity.status(HttpStatus.CREATED).body(timeCriado);
        } catch (CampeonatoInvalidoException | TimeInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PasswordInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCampeonato}/times")
    @PermitAll
    public ResponseEntity<List<TimeDto>> listarTimesCampeonato(@PathVariable Long idCampeonato) {
        try {
            List<TimeDto> campeonatos = campeonatoService.listarTimesCampeonato(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(campeonatos);
        } catch (RegistroNaoEncontradoException  e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/times/{idAcademico}")
    @PermitAll
    public ResponseEntity<JogadorDto> adicionarJogadorTime(@RequestBody TimeDto timeDto, @PathVariable Long idAcademico) {
        try {
            JogadorDto jogador = campeonatoService.adicionarJogadorTime(timeDto, idAcademico);
            return ResponseEntity.status(HttpStatus.CREATED).body(jogador);
        } catch (CampeonatoInvalidoException | TimeInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PasswordInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idCampeonato}/times/{idAcademico}")
    @PermitAll
    public ResponseEntity<TimeDto> criarTimeComUmJogador(@PathVariable Long idCampeonato, @PathVariable Long idAcademico, @RequestBody String senhaCampeonato) {
        try {
            TimeDto timeCriado = campeonatoService.criarTimeComUmJogador(idCampeonato, idAcademico, senhaCampeonato);
            return ResponseEntity.status(HttpStatus.CREATED).body(timeCriado);
        } catch (CampeonatoInvalidoException | TimeInvalidoException | RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (PasswordInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idCampeonato}/jogadores")
    @PermitAll
    public ResponseEntity<List<JogadorDto>> listarJogadoresPorCampeonato(@PathVariable Long idCampeonato) {
        try {
            List<JogadorDto> jogadores = campeonatoService.listarJogadoresCampeonato(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(jogadores);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idAcademico}/jogadores-enfrentados")
    public ResponseEntity<List<AcademicoDto>> listarJogadoresEnfrentados(@PathVariable Long idAcademico) {
        try {
            List<AcademicoDto> jogadoresEnfrentados = campeonatoService.listarJogadoresEnfrentados(idAcademico);
            return ResponseEntity.ok(jogadoresEnfrentados);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/situacao")
    @PermitAll
    public ResponseEntity<JogadorDto> mudarSituacaoJogador(@PathVariable Long id, @RequestParam String situacao) {
        try {
            JogadorDto jogadorAtualizado = campeonatoService.mudarSituacaoJogador(id, situacao);
            return ResponseEntity.ok(jogadorAtualizado);
        } catch (TipoInvalidoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{idCampeonato}/primeira-fase")
    @PermitAll
    public ResponseEntity<List<PartidaDto>> definirPrimeiraFase(@PathVariable Long idCampeonato) {
        try {
            List<PartidaDto> partidas = campeonatoService.definirPrimeiraFase(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(partidas);
        } catch (RegistroNaoEncontradoException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idCampeonato}/partidas")
    @PermitAll
    public ResponseEntity<List<PartidaDto>> listarPartidas(@PathVariable Long idCampeonato) {
        try {
            List<PartidaDto> partidas = campeonatoService.listarPartidas(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(partidas);
        } catch (RegistroNaoEncontradoException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idCampeonato}/avancar-fase")
    @PermitAll
    public ResponseEntity<List<PartidaDto>> avancarDeFase(@PathVariable Long idCampeonato) {
        try {
            List<PartidaDto> partidas = campeonatoService.avancarDeFase(idCampeonato);
            return ResponseEntity.status(HttpStatus.OK).body(partidas);
        }catch (RegistroNaoEncontradoException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (AvancarFaseException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch(Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/partidas/{idPartida}/pontuacao")
    @PermitAll
    public ResponseEntity<PartidaDto> alterarPontuacaoPartida(@PathVariable Long idPartida, @RequestParam int pontuacaoTime1, @RequestParam int pontuacaoTime2) {
        try {
            PartidaDto partida = campeonatoService.alterarPontuacaoPartida(idPartida, pontuacaoTime1, pontuacaoTime2);
            return ResponseEntity.status(HttpStatus.OK).body(partida);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/avaliar")
    @PermitAll
    public ResponseEntity<AvaliacaoJogadorDto> avaliarJogador(@RequestParam Long idAvaliador, @RequestParam Long idJogador, @RequestParam int nota) {
        try {
            AvaliacaoJogadorDto avaliacao = campeonatoService.avaliarJogador(idAvaliador, idJogador, nota);
            return ResponseEntity.ok(avaliacao);
        } catch (AcademicoNaoExisteException | RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idCampeonato}/avaliacao/{idAcademico}")
    @PermitAll
    public ResponseEntity<Float> recuperaAvaliacaoNoCampeonato(@PathVariable Long idCampeonato, @PathVariable Long idAcademico) {
        try {
            float media = campeonatoService.recuperaAvaliacaoNoCampeonato(idCampeonato, idAcademico);
            return ResponseEntity.ok(media);
        } catch (AcademicoNaoExisteException | CampeonatoInvalidoException | RegistroNaoEncontradoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/avaliacao/{idAcademico}/modalidade/{idModalidade}")
    @PermitAll
    public ResponseEntity<Float> recuperaAvaliacaoPorModalidade(@PathVariable Long idModalidade, @PathVariable Long idAcademico) {
        try {
            float media = campeonatoService.recuperaAvaliacaoPorModalidade(idModalidade, idAcademico);
            return ResponseEntity.ok(media);
        } catch (AcademicoNaoExisteException | ModalidadeNaoExistenteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/historico/{idAcademico}")
    @PermitAll
    public ResponseEntity<List<CampeonatoResponseDto>> buscarHistoricoCampeonatoOutrosUsuarios( @PathVariable Long idAcademico) {
        try {
            List<CampeonatoResponseDto> campeonatos = campeonatoService.buscarHistoricoCampeonatoOutrosUsuarios(idAcademico);
            return ResponseEntity.ok(campeonatos);
        } catch (AcademicoNaoExisteException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ConteudoPrivadoException e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

