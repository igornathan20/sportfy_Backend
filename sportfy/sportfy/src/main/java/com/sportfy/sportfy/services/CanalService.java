package com.sportfy.sportfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.sportfy.sportfy.models.Canal;
import com.sportfy.sportfy.models.Comentario;
import com.sportfy.sportfy.models.CurtidaComentario;
import com.sportfy.sportfy.models.CurtidaPublicacao;
import com.sportfy.sportfy.models.Publicacao;
import com.sportfy.sportfy.repositories.CanalRepository;
import com.sportfy.sportfy.repositories.ComentarioRepository;
import com.sportfy.sportfy.repositories.CurtidaComentarioRepository;
import com.sportfy.sportfy.repositories.CurtidaPublicacaoRepository;
import com.sportfy.sportfy.repositories.PublicacaoRepository;

import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CanalService {

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private CurtidaPublicacaoRepository curtidaPublicacaoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private CurtidaComentarioRepository curtidaComentarioRepository;

    public PublicacaoDto cadastrarPublicacao(PublicacaoDto publicacaoDto) {
        Publicacao novoPublicacao = new Publicacao();
        novoPublicacao.cadastrar(publicacaoDto);
        Publicacao publicacaoCriada = publicacaoRepository.save(novoPublicacao);
        return PublicacaoDto.fromPublicacaoBD(publicacaoCriada);
    }

    public PublicacaoDto atualizarPublicacao(Long idPublicacao, PublicacaoDto publicacaoDto) throws PublicacaoNaoExisteException {
        Publicacao publicacaoExistente = publicacaoRepository.findById(idPublicacao).orElse(null);
        if (publicacaoExistente != null) {
            Publicacao publicacaoAtualizada = publicacaoExistente;
            publicacaoAtualizada.setDescricao(publicacaoDto.descricao());
            Publicacao publicacaoSalva = publicacaoRepository.save(publicacaoAtualizada);
            return PublicacaoDto.fromPublicacaoBD(publicacaoSalva);
        } else {
            throw new PublicacaoNaoExisteException("Publicacão não existe!");
        }
    }

    public PublicacaoDto removerPublicacao(Long idPublicacao) throws PublicacaoNaoExisteException {
        return publicacaoRepository.findById(idPublicacao).map(publicacaoBD -> {
            publicacaoRepository.delete(publicacaoBD);
            return PublicacaoDto.fromPublicacaoBD(publicacaoBD);
        }).orElseThrow(() -> new PublicacaoNaoExisteException("Publicacão não existe!"));
    }

    public boolean curtirPublicacao(Long idUsuario, Long idPublicacao) throws UsuarioCurtidaPublicacaoJaExisteException, PublicacaoNaoExisteException{
        Optional<Publicacao> publicacaoBD = publicacaoRepository.findById(idPublicacao);
        if (publicacaoBD.isPresent()) {
            for (CurtidaPublicacao curtidaPublicacao : publicacaoBD.get().getListaCurtidaPublicacao()) {
                if (curtidaPublicacao.getUsuario().getIdUsuario() == idUsuario) {
                    throw new UsuarioCurtidaPublicacaoJaExisteException("Usuário já curtiu a publicação!");
                }
            }
            CurtidaPublicacao novaCurtidaPublicacao = new CurtidaPublicacao();
            novaCurtidaPublicacao.cadastrar(idUsuario, idPublicacao);
            curtidaPublicacaoRepository.save(novaCurtidaPublicacao);
            return true;
        } else {
            throw new PublicacaoNaoExisteException("Publicacão não existe!");
        }
    }
    
    @Transactional
    public boolean removerCurtidaPublicacao(Long idUsuario, Long idPublicacao) throws UsuarioCurtidaPublicacaoNaoExisteException, PublicacaoNaoExisteException{
        Optional<Publicacao> publicacaoBD = publicacaoRepository.findById(idPublicacao);
        if (publicacaoBD.isPresent()) {
            for (CurtidaPublicacao curtidaPublicacao : publicacaoBD.get().getListaCurtidaPublicacao()) {
                if (curtidaPublicacao.getUsuario().getIdUsuario() == idUsuario) {
                    curtidaPublicacaoRepository.deleteCurtidaById(curtidaPublicacao.getIdCurtidaPublicacao());
                    return true;
                }
            }
            throw new UsuarioCurtidaPublicacaoNaoExisteException("Usuário não curtiu a publicação!");
        } else {
            throw new PublicacaoNaoExisteException("Publicacão não existe!");
        }
    }

    public ComentarioDto cadastrarComentario(ComentarioDto comentarioDto) {
        Comentario novoComentario = new Comentario();
        novoComentario.cadastrar(comentarioDto);
        Comentario comentarioCriado = comentarioRepository.save(novoComentario);
        return ComentarioDto.fromComentarioBD(comentarioCriado);
    }

    public ComentarioDto atualizarComentario(Long idComentario, ComentarioDto comentarioDto) throws ComentarioNaoExisteException {
        Comentario comentarioExistente = comentarioRepository.findById(idComentario).orElse(null);
        if (comentarioExistente != null) {
            Comentario comentarioAtualizado = comentarioExistente;
            comentarioAtualizado.setDescricao(comentarioDto.descricao());
            Comentario comentarioSalvo = comentarioRepository.save(comentarioAtualizado);
            return ComentarioDto.fromComentarioBD(comentarioSalvo);
        } else {
            throw new ComentarioNaoExisteException("Comentario não existe!");
        }
    }
    
    @Transactional
    public ComentarioDto removerComentario(Long idComentario) throws ComentarioNaoExisteException {
        Comentario comentarioExistente = comentarioRepository.findById(idComentario).orElse(null);
        if (comentarioExistente != null) {
            if (comentarioExistente.getListaCurtidaComentario() != null && !comentarioExistente.getListaCurtidaComentario().isEmpty()) {
                List<Long> listaIdCurtidaComentario = new ArrayList<Long>();
                for (CurtidaComentario curtidaComentario : comentarioExistente.getListaCurtidaComentario()) {
                    listaIdCurtidaComentario.add(curtidaComentario.getIdCurtidaComentario());
                }
                curtidaComentarioRepository.deleteAllById(listaIdCurtidaComentario);
            }
            comentarioRepository.deleteComentarioById(comentarioExistente.getIdComentario());
            return ComentarioDto.fromComentarioBD(comentarioExistente);
        } else {
            throw new ComentarioNaoExisteException("Comentario não existe!");
        }
    }

    public boolean curtirComentario(Long idUsuario, Long idComentario) throws UsuarioCurtidaComentarioJaExisteException, ComentarioNaoExisteException{
        Optional<Comentario> comentarioBD = comentarioRepository.findById(idComentario);
        if (comentarioBD.isPresent()) {
            for (CurtidaComentario curtidaComentario : comentarioBD.get().getListaCurtidaComentario()) {
                if (curtidaComentario.getUsuario().getIdUsuario() == idUsuario) {
                    throw new UsuarioCurtidaComentarioJaExisteException("Usuário já curtiu o comentário!");
                }
            }
            CurtidaComentario novaCurtidaComentario = new CurtidaComentario();
            novaCurtidaComentario.cadastrar(idUsuario, idComentario);
            curtidaComentarioRepository.save(novaCurtidaComentario);
            return true;
        } else {
            throw new ComentarioNaoExisteException("Comentario não existe!");
        }
    }

    public boolean removerCurtidaComentario(Long idUsuario, Long idComentario) throws UsuarioCurtidaComentarioNaoExisteException, ComentarioNaoExisteException{
        Optional<Comentario> comentarioBD = comentarioRepository.findById(idComentario);
        if (comentarioBD.isPresent()) {
            for (CurtidaComentario curtidaComentario : comentarioBD.get().getListaCurtidaComentario()) {
                if (curtidaComentario.getUsuario().getIdUsuario() == idUsuario) {
                    curtidaComentarioRepository.delete(curtidaComentario);
                    return true;
                }
            }
            throw new UsuarioCurtidaComentarioNaoExisteException("Usuário não curtiu o comentário!");
        } else {
            throw new ComentarioNaoExisteException("Comentario não existe!");
        }
    }

    public List<CanalDto> listar() throws ListaCanalVazioException {
        List<Canal> listaCanalBD = canalRepository.findAll();
        if (!listaCanalBD.isEmpty()) {
            List<CanalDto> listaCanalDto = listaCanalBD.stream().map(canalBD -> CanalDto.fromCanalBD(canalBD)).collect(Collectors.toList());
            return listaCanalDto;
        } else {
            throw new ListaCanalVazioException("Lista de canais vazio!");
        }
    }
}
