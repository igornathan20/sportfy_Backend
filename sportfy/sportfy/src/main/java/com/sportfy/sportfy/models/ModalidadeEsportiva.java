package com.sportfy.sportfy.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.sportfy.sportfy.dtos.ModalidadeEsportivaDto;
import com.sportfy.sportfy.repositories.MetaEsportivaRepository;
import com.sportfy.sportfy.repositories.RegraRepository;

import org.hibernate.annotations.CreationTimestamp;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="modalidade_esportiva")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeEsportiva implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_modalidade_esportiva")
    private Long idModalidadeEsportiva;

    @Column(name="nome", unique = true)
    private String nome;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="foto")
    private String foto;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_criacao", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name="ativo", insertable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy="modalidadeEsportiva", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<MetaEsportiva> listaMetaEsportiva;

//    public void cadastrar(ModalidadeEsportivaDto modalidadeEsportivaDto) {
//        List<Regra> listaRegra = new ArrayList<Regra>();
//        List<MetaEsportiva> listaMetaEsportiva = new ArrayList<MetaEsportiva>();
//        if (modalidadeEsportivaDto.listaRegra() != null && !modalidadeEsportivaDto.listaRegra().isEmpty()) {
//            for (RegraDto regraDto : modalidadeEsportivaDto.listaRegra()) {
//                Regra regra = new Regra();
//                regra.cadastrar(regraDto);
//                regra.setModalidadeEsportiva(this);
//                listaRegra.add(regra);
//            }
//        }
//        if (modalidadeEsportivaDto.listaMetaEsportiva() != null && !modalidadeEsportivaDto.listaMetaEsportiva().isEmpty()) {
//            for (MetaEsportivaDto metaEsportivaDto : modalidadeEsportivaDto.listaMetaEsportiva()) {
//                MetaEsportiva metaEsportiva = new MetaEsportiva();
//                metaEsportiva.cadastrar(metaEsportivaDto);
//                metaEsportiva.setModalidadeEsportiva(this);
//                listaMetaEsportiva.add(metaEsportiva);
//            }
//        }
//        this.setNome(modalidadeEsportivaDto.nome());
//        this.setDescricao(modalidadeEsportivaDto.descricao());
//        this.setFoto(modalidadeEsportivaDto.foto());
//        this.setListaMetaEsportiva(listaMetaEsportiva);
//    }

//    public void atualizar(Long idModalidadeEsportiva, ModalidadeEsportivaDto modalidadeEsportivaDto, MetaEsportivaRepository metaEsportivaRepository, RegraRepository regraRepository) {
//
//        // --- Atualização de MetaEsportiva ---
//
//        List<MetaEsportiva> listaMetaEsportiva = new ArrayList<>();
//
//        // Pega as metas esportivas atuais associadas a essa modalidade
//        List<MetaEsportiva> metasAtuais = metaEsportivaRepository.findByModalidadeEsportivaIdModalidadeEsportiva(this.idModalidadeEsportiva);
//
//        // Lista de IDs das metas que estão sendo recebidas do front
//        List<Long> idsRecebidosMetas = modalidadeEsportivaDto.listaMetaEsportiva().stream().map(MetaEsportivaDto::idMetaEsportiva).filter(Objects::nonNull).toList();
//
//        // Verifica quais metas não estão mais na nova lista e marca como inativas
//        for (MetaEsportiva metaExistente : metasAtuais) {
//            if (!idsRecebidosMetas.contains(metaExistente.getIdMetaEsportiva())) {
//                metaExistente.setAtivo(false);
//                metaEsportivaRepository.save(metaExistente); // Atualiza a meta no banco
//            }
//        }
//
//        // Atualiza ou adiciona novas metas
//        if (modalidadeEsportivaDto.listaMetaEsportiva() != null && !modalidadeEsportivaDto.listaMetaEsportiva().isEmpty()) {
//            for (MetaEsportivaDto metaEsportivaDto : modalidadeEsportivaDto.listaMetaEsportiva()) {
//                MetaEsportiva metaEsportiva;
//
//                // Verifica se a meta já existe no banco
//                if (metaEsportivaDto.idMetaEsportiva() != null) {
//                    Optional<MetaEsportiva> metaExistente = metaEsportivaRepository.findById(metaEsportivaDto.idMetaEsportiva());
//                    if (metaExistente.isPresent()) {
//                        metaEsportiva = metaExistente.get();
//                        metaEsportiva.setAtivo(true); // Se ainda está na lista, mantemos ativo
//                    } else {
//                        metaEsportiva = new MetaEsportiva(); // Se não existir, cria uma nova meta
//                    }
//                } else {
//                    metaEsportiva = new MetaEsportiva(); // Cria uma nova meta se o ID não for fornecido
//                }
//
//                metaEsportiva.cadastrar(metaEsportivaDto);
//                metaEsportiva.setModalidadeEsportiva(this);
//                listaMetaEsportiva.add(metaEsportiva);
//            }
//        }
//
//        // --- Atualização de Regras ---
//
//        List<Regra> listaRegra = new ArrayList<>();
//
//        // Pega as regras atuais associadas a essa modalidade
//        List<Regra> regrasAtuais = regraRepository.findByModalidadeEsportivaIdModalidadeEsportiva(this.idModalidadeEsportiva);
//
//        // Lista de IDs das regras que estão sendo recebidas do front
//        List<Long> idsRecebidosRegras = modalidadeEsportivaDto.listaRegra().stream()
//                .map(RegraDto::idRegra)
//                .filter(Objects::nonNull)
//                .toList();
//
//        // Verifica quais regras não estão mais na nova lista e remove
//        for (Regra regraExistente : regrasAtuais) {
//            if (!idsRecebidosRegras.contains(regraExistente.getIdRegra())) {
//                regraRepository.delete(regraExistente); // Remove a regra do banco
//            }
//        }
//
//        // Atualiza ou adiciona novas regras
//        if (modalidadeEsportivaDto.listaRegra() != null && !modalidadeEsportivaDto.listaRegra().isEmpty()) {
//            for (RegraDto regraDto : modalidadeEsportivaDto.listaRegra()) {
//                Regra regra;
//
//                // Verifica se a regra já existe no banco
//                if (regraDto.idRegra() != null) {
//                    Optional<Regra> regraExistente = regraRepository.findById(regraDto.idRegra());
//                    if (regraExistente.isPresent()) {
//                        regra = regraExistente.get();
//                    } else {
//                        regra = new Regra(); // Cria uma nova regra se o ID não for encontrado
//                    }
//                } else {
//                    regra = new Regra(); // Cria uma nova regra se o ID não for fornecido
//                }
//
//                regra.cadastrar(regraDto);
//                regra.setModalidadeEsportiva(this);
//                listaRegra.add(regra);
//            }
//        }
//
//        // Atualiza a modalidade esportiva
//        this.idModalidadeEsportiva = idModalidadeEsportiva;
//        this.setNome(modalidadeEsportivaDto.nome());
//        this.setDescricao(modalidadeEsportivaDto.descricao());
//        this.setFoto(modalidadeEsportivaDto.foto());
//        this.setListaMetaEsportiva(listaMetaEsportiva);
//        this.setListaRegra(listaRegra);
//    }

    public void inativar(MetaEsportivaRepository metaEsportivaRepository, RegraRepository regraRepository) {
        // Inativar a modalidade esportiva
        this.setAtivo(false);
        
        // Excluir todas as regras associadas a essa modalidade
        List<Regra> regrasAtuais = regraRepository.findByModalidadeEsportivaIdModalidadeEsportiva(this.idModalidadeEsportiva);
        for (Regra regra : regrasAtuais) {
            regraRepository.delete(regra); // Remove a regra do banco
        }
    
        // Inativar todas as metas associadas a essa modalidade
        List<MetaEsportiva> metasAtuais = metaEsportivaRepository.findByModalidadeEsportivaIdModalidadeEsportiva(this.idModalidadeEsportiva);
        for (MetaEsportiva meta : metasAtuais) {
            meta.setAtivo(false); // Marca a meta como inativa
            metaEsportivaRepository.save(meta); // Atualiza a meta no banco
        }
    }

    public void fromDto(ModalidadeEsportivaDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("O DTO não pode ser nulo.");
        }

        if (dto.nome() != null) {
            this.setNome(dto.nome());
        }

        if (dto.descricao() != null) {
            this.setDescricao(dto.descricao());
        }

        if (dto.foto() != null) {
            this.setFoto(dto.foto());
        }
    }
}
