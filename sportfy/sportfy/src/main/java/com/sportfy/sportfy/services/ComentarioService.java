package com.sportfy.sportfy.services;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.ComentarioDto;
import com.sportfy.sportfy.exeptions.ComentarioNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaComentarioJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaComentarioNaoExisteException;
import com.sportfy.sportfy.models.Comentario;
import com.sportfy.sportfy.models.CurtidaComentario;
import com.sportfy.sportfy.repositories.ComentarioRepository;
import com.sportfy.sportfy.repositories.CurtidaComentarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private CurtidaComentarioRepository curtidaComentarioRepository;

    public ComentarioDto cadastrarComentario(ComentarioDto comentarioDto) {
        Comentario novoComentario = new Comentario();
        novoComentario.cadastrar(comentarioDto);
        Comentario comentarioCriado = comentarioRepository.save(novoComentario);
        return ComentarioDto.fromComentarioBD(comentarioCriado);
    }

    public ComentarioDto atualizarComentario(Long idComentario, ComentarioDto comentarioDto, String usuarioAutenticado) throws ComentarioNaoExisteException, AccessDeniedException {
        Comentario comentarioExistente = comentarioRepository.findById(idComentario).orElse(null);
        if (comentarioExistente != null) {
            if (comentarioExistente.getUsuario().getUsername().equals(usuarioAutenticado)) {
                Comentario comentarioAtualizado = comentarioExistente;
                comentarioAtualizado.setDescricao(comentarioDto.descricao());
                Comentario comentarioSalvo = comentarioRepository.save(comentarioAtualizado);
                return ComentarioDto.fromComentarioBD(comentarioSalvo);
            } else {
                throw new AccessDeniedException("Usuário não tem permissão para atualizar o comentário!");
            }
        } else {
            throw new ComentarioNaoExisteException("Comentario não existe!");
        }
    }
    
    @Transactional
    public ComentarioDto removerComentario(Long idComentario, String usuarioAutenticado) throws ComentarioNaoExisteException, AccessDeniedException {
        Comentario comentarioExistente = comentarioRepository.findById(idComentario).orElse(null);
        if (comentarioExistente != null) {
            if (comentarioExistente.getUsuario().getUsername().equals(usuarioAutenticado)) {
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
                throw new AccessDeniedException("Usuário não tem permissão para remover o comentário!");
            }
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

    public Page<ComentarioDto> listarComentariosPorPublicacao(Long idPublicacao, Pageable pageable) throws ComentarioNaoExisteException {
        Page<Comentario> comentariosBD = comentarioRepository.findByPublicacaoIdPublicacao(idPublicacao, pageable);
        
        if (comentariosBD.isEmpty()) {
            throw new ComentarioNaoExisteException("Nenhum comentário encontrado para esta publicação.");
        }
        
        return comentariosBD.map(ComentarioDto::fromComentarioBD);
    }
}
