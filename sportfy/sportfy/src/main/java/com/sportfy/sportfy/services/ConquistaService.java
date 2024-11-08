package com.sportfy.sportfy.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportfy.sportfy.dtos.ConquistaDto;
import com.sportfy.sportfy.dtos.MetaEsportivaConquistaDto;
import com.sportfy.sportfy.dtos.MetaEsportivaDto;
import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.exeptions.ConquistaJaExistenteException;
import com.sportfy.sportfy.exeptions.MetaEsportivaNaoExisteException;
import com.sportfy.sportfy.models.Conquista;
import com.sportfy.sportfy.models.MetaEsportiva;
import com.sportfy.sportfy.repositories.ConquistaRepository;
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;

@Service
public class ConquistaService {
    @Autowired
    private ConquistaRepository conquistaRepository;

    @Autowired
    private MetaEsportivaRepository metaEsportivaRepository;

    public ConquistaDto conquistar(Long idAcademico, Long idMetaEsportiva) throws ConquistaJaExistenteException {
        Optional<Conquista> conquistaBD = conquistaRepository.findByAcademicoIdAcademicoAndMetaEsportivaIdMetaEsportiva(idAcademico, idMetaEsportiva);
        if (!conquistaBD.isEmpty()) {
            throw new ConquistaJaExistenteException("Conquista já existente!");
        }
        Conquista novaConquista = new Conquista();
        novaConquista.cadastrar(idAcademico, idMetaEsportiva);
        Conquista conquistaCriada = conquistaRepository.save(novaConquista);
        return ConquistaDto.fromConquistaBD(conquistaCriada);
    }

    public List<MetaEsportivaConquistaDto> listarConquistas(Long idAcademico) throws MetaEsportivaNaoExisteException {
        List<Conquista> listaConquistaBD = conquistaRepository.findByAcademicoIdAcademico(idAcademico);
        List<MetaEsportiva> listaMetaEsportiva = metaEsportivaRepository.findByModalidadeEsportivaAtivoAndAtivo(true, true);

        if (listaMetaEsportiva.isEmpty()) {
            throw new MetaEsportivaNaoExisteException("Lista de metas esportivas vazia!");
        }

        Set<Long> conquistaIds = new HashSet<>();
        Map<Long, Conquista> conquistaMap = new HashMap<>();
        for (Conquista conquista : listaConquistaBD) {
            conquistaIds.add(conquista.getMetaEsportiva().getIdMetaEsportiva());
            conquistaMap.put(conquista.getMetaEsportiva().getIdMetaEsportiva(), conquista);
        }

        List<MetaEsportivaConquistaDto> listaMetaEsportivaConquista = new ArrayList<>();
        for (MetaEsportiva metaEsportiva : listaMetaEsportiva) {
            boolean conquistada = conquistaIds.contains(metaEsportiva.getIdMetaEsportiva());
            Conquista conquistaCorrespondente = conquistaMap.get(metaEsportiva.getIdMetaEsportiva());

            listaMetaEsportivaConquista.add(new MetaEsportivaConquistaDto(
                    conquistada,
                    conquistada ? conquistaCorrespondente.getDataConquista() : null,
                    MetaEsportivaDto.toDto(metaEsportiva),
                    ModalidadeEsportivaDto.toDTO(metaEsportiva.getModalidadeEsportiva())
            ));
        }
        return listaMetaEsportivaConquista;
    }
}
