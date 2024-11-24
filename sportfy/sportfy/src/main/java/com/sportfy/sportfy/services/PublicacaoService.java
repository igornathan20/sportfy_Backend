package com.sportfy.sportfy.services;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.PublicacaoDto;
import com.sportfy.sportfy.exeptions.PublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioNaoExisteException;
import com.sportfy.sportfy.models.CurtidaPublicacao;
import com.sportfy.sportfy.models.Publicacao;
import com.sportfy.sportfy.models.Usuario;
import com.sportfy.sportfy.repositories.CurtidaPublicacaoRepository;
import com.sportfy.sportfy.repositories.PublicacaoRepository;
import com.sportfy.sportfy.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PublicacaoService {

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private CurtidaPublicacaoRepository curtidaPublicacaoRepository;

    public PublicacaoDto cadastrarPublicacao(PublicacaoDto publicacaoDto) {
        Publicacao novoPublicacao = new Publicacao();
        novoPublicacao.cadastrar(publicacaoDto);
        Publicacao publicacaoCriada = publicacaoRepository.save(novoPublicacao);
        return PublicacaoDto.fromPublicacaoBD(publicacaoCriada);
    }

    public PublicacaoDto atualizarPublicacao(Long idPublicacao, PublicacaoDto publicacaoDto, String usuarioAutenticado) throws PublicacaoNaoExisteException, AccessDeniedException {
        Publicacao publicacaoExistente = publicacaoRepository.findById(idPublicacao).orElse(null);
        if (publicacaoExistente != null) {
            if (publicacaoExistente.getUsuario().getUsername().equals(usuarioAutenticado)) {
                Publicacao publicacaoAtualizada = publicacaoExistente;
                publicacaoAtualizada.setTitulo(publicacaoDto.titulo());
                publicacaoAtualizada.setDescricao(publicacaoDto.descricao());
                Publicacao publicacaoSalva = publicacaoRepository.save(publicacaoAtualizada);
                return PublicacaoDto.fromPublicacaoBD(publicacaoSalva);
            } else {
                throw new AccessDeniedException("Usuário não tem permissão para atualizar a publicação!");
            }
        } else {
            throw new PublicacaoNaoExisteException("Publicacão não existe!");
        }
    }

    public PublicacaoDto removerPublicacao(Long idPublicacao, String usuarioAutenticado) throws PublicacaoNaoExisteException, AccessDeniedException {
        Publicacao publicacaoExistente = publicacaoRepository.findById(idPublicacao).orElse(null);
        if (publicacaoExistente != null) {
            if (publicacaoExistente.getUsuario().getUsername().equals(usuarioAutenticado)) {
                publicacaoRepository.delete(publicacaoExistente);
                return PublicacaoDto.fromPublicacaoBD(publicacaoExistente);
            } else {
                throw new AccessDeniedException("Usuário não tem permissão para remover a publicação!");
            }
        } else {
            throw new PublicacaoNaoExisteException("Publicacão não existe!");
        }
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

    public Page<PublicacaoDto> listarPublicacoesPorCanal(Long idCanal, Pageable pageable) throws PublicacaoNaoExisteException {
        Page<Publicacao> publicacoesBD = publicacaoRepository.findByCanalIdCanal(idCanal, pageable);
        
        if (publicacoesBD.isEmpty()) {
            throw new PublicacaoNaoExisteException("Nenhuma publicação encontrada para este canal.");
        }
        
        return publicacoesBD.map(PublicacaoDto::fromPublicacaoBD);
    }

    public Page<PublicacaoDto> listarPublicacoesPorUsuario(Long idCanal, String username, Pageable pageable) throws UsuarioNaoExisteException, PublicacaoNaoExisteException {
        Usuario usuarioBD = usuarioRepository.findByUsername(username);
        if (usuarioBD == null) {
            throw new UsuarioNaoExisteException("Usuário não encontrado.");
        }

        Page<Publicacao> publicacoesBD = publicacaoRepository.findByCanalIdCanalAndUsuarioUsername(idCanal, username, pageable);
        if (publicacoesBD.isEmpty()) {
            throw new PublicacaoNaoExisteException("Nenhuma publicação encontrada para este usuario.");
        }
        
        return publicacoesBD.map(PublicacaoDto::fromPublicacaoBD);
    }
}
