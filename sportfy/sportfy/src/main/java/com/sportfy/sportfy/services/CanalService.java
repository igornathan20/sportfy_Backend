package com.sportfy.sportfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.CanalDto;
import com.sportfy.sportfy.exeptions.ListaCanalVazioException;
import com.sportfy.sportfy.models.UsuarioCanal;
import com.sportfy.sportfy.repositories.UsuarioCanalRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CanalService {

    @Autowired
    UsuarioCanalRepository usuarioCanalRepository;

    public List<CanalDto> listarCanais(Long idUsuario) throws ListaCanalVazioException {
        List<UsuarioCanal> listaUsuarioCanalBD = usuarioCanalRepository.findByUsuarioIdUsuario(idUsuario);
        if (!listaUsuarioCanalBD.isEmpty()) {
            List<CanalDto> listaCanalDto = listaUsuarioCanalBD.stream().map(usuarioCanalBD -> CanalDto.fromCanalBD(usuarioCanalBD.getCanal())).collect(Collectors.toList());
            return listaCanalDto;
        } else {
            throw new ListaCanalVazioException("Lista de canais vazio para esse usuário!");
        }
    }
}
