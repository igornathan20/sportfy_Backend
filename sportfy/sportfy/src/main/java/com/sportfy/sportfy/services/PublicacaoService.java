package com.sportfy.sportfy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.PublicacaoDto;
import com.sportfy.sportfy.exeptions.PublicacaoNaoExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoJaExisteException;
import com.sportfy.sportfy.exeptions.UsuarioCurtidaPublicacaoNaoExisteException;
import com.sportfy.sportfy.models.CurtidaPublicacao;
import com.sportfy.sportfy.models.Publicacao;
import com.sportfy.sportfy.repositories.CurtidaPublicacaoRepository;
import com.sportfy.sportfy.repositories.PublicacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class PublicacaoService {

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Autowired
    private CurtidaPublicacaoRepository curtidaPublicacaoRepository;

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
            publicacaoAtualizada.setTitulo(publicacaoDto.titulo());
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

    public Page<PublicacaoDto> listarPublicacoesPorCanal(Long idCanal, Pageable pageable) throws PublicacaoNaoExisteException {
        Page<Publicacao> publicacoesBD = publicacaoRepository.findByCanalIdCanal(idCanal, pageable);
        
        if (publicacoesBD.isEmpty()) {
            throw new PublicacaoNaoExisteException("Nenhuma publicação encontrada para este canal.");
        }
        
        return publicacoesBD.map(PublicacaoDto::fromPublicacaoBD);
    }
}
