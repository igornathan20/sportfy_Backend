package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.CampeonatoDto;
import com.sportfy.sportfy.exeptions.AcademicoNaoExisteException;
import com.sportfy.sportfy.exeptions.ModalidadeNaoExistenteException;
import com.sportfy.sportfy.exeptions.RegistroNaoEncontradoException;
import com.sportfy.sportfy.models.*;
import com.sportfy.sportfy.repositories.AcademicoRepository;
import com.sportfy.sportfy.repositories.CampeonatoRepository;
import com.sportfy.sportfy.repositories.EnderecoRepository;
import com.sportfy.sportfy.repositories.ModalidadeEsportivaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CampeonatoService {
    @Autowired
    CampeonatoRepository campeonatoRepository;
    @Autowired
    AcademicoRepository academicoRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    ModalidadeEsportivaRepository modalidadeEsportivaRepository;

    public Campeonato criarCampeonato(CampeonatoDto campeonatoDto) throws AcademicoNaoExisteException, ModalidadeNaoExistenteException{
        Optional<Academico> academico = academicoRepository.findById(campeonatoDto.idAcademico());
        Optional<ModalidadeEsportiva> modalidade = modalidadeEsportivaRepository.findById(campeonatoDto.idModalidadeEsportiva());

        if(academico.isPresent()){
            if (modalidade.isPresent()){
                Campeonato novoCampeonato = new Campeonato();
                try {
                    novoCampeonato.toEntity(campeonatoDto);
                    novoCampeonato.setAcademico(academico.get());
                    novoCampeonato.setModalidadeEsportiva(modalidade.get());
                    novoCampeonato.setCodigo("#" + gerarCodigoUnico());
                    Endereco enderecoCampeonato = new Endereco();
                    enderecoCampeonato.toEntity(campeonatoDto.endereco());
                    novoCampeonato.setEndereco(enderecoCampeonato);
                    return campeonatoRepository.save(novoCampeonato);
                }catch (Exception e){
                    return null;
                }
            }else {
                throw new ModalidadeNaoExistenteException("Modalidade esportiva nao cadastrada!");
            }
        }else{
            throw new AcademicoNaoExisteException("Academico não encontrado!");
        }
    }

    public Campeonato editarCampeonato(CampeonatoDto campeonatoDto) throws RegistroNaoEncontradoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(campeonatoDto.idCampeonato());

        if(campeonato.isPresent()){
            Campeonato editCampeonato = campeonato.get();
            try {
                editCampeonato.toEntity(campeonatoDto);
                Endereco enderecoCampeonato = editCampeonato.getEndereco();
                enderecoCampeonato.toEntity(campeonatoDto.endereco());
                editCampeonato.setEndereco(enderecoCampeonato);

                return campeonatoRepository.save(editCampeonato);
            }catch (Exception e){
                return null;
            }
        }else{
            throw new RegistroNaoEncontradoException("Campeonato não encontrado!");
        }
    }

    public List<Campeonato> listarTodosCampeonatos() throws RegistroNaoEncontradoException {
        List<Campeonato> campeonatos = campeonatoRepository.findAll();

        if (campeonatos.isEmpty()){
            throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
        }else {
            return campeonatos;
        }
    }

    public List<Campeonato> listarCampeonatosComFiltro(CampeonatoDto campeonatoDto) throws RegistroNaoEncontradoException {
        Specification<Campeonato> spec = Specification.where(null);

        if (campeonatoDto.codigo() != null && !campeonatoDto.codigo().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("codigo"), campeonatoDto.codigo()));
        }

        if (campeonatoDto.titulo() != null && !campeonatoDto.titulo().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + campeonatoDto.titulo().toLowerCase() + "%"));
        }

        if (campeonatoDto.dataInicio() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("dataInicio"), campeonatoDto.dataInicio()));
        }

        if (campeonatoDto.dataFim() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("dataFim"), campeonatoDto.dataFim()));
        }

        if (campeonatoDto.ativo()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("ativo"), campeonatoDto.ativo()));
        }

        if (campeonatoDto.privacidadeCampeonato() != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("privacidadeCampeonato"), campeonatoDto.privacidadeCampeonato()));
        }

        if (campeonatoDto.idAcademico() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idAcademico"), campeonatoDto.idAcademico()));
        }

        if (campeonatoDto.idModalidadeEsportiva() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idModalidadeEsportiva"), campeonatoDto.idModalidadeEsportiva()));
        }

        if (campeonatoDto.situacaoCampeonato() != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("situacaoCampeonato"), campeonatoDto.situacaoCampeonato()));
        }

        List<Campeonato> campeonatos = campeonatoRepository.findAll(spec);

        if (campeonatos.isEmpty()) {
            throw new RegistroNaoEncontradoException("Nenhum campeonato encontrado!");
        }
        return campeonatos;
    }

    public Optional<Campeonato> excluirCampeonato(Long id)throws RegistroNaoEncontradoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);

        if (campeonato.isPresent()){
            campeonatoRepository.deleteById(id);
            return campeonato;
        }else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    public Campeonato desativarCampeonato(Long id) throws RegistroNaoEncontradoException{
        Optional<Campeonato> campeonato = campeonatoRepository.findById(id);

        if (campeonato.isPresent()){
            Campeonato campeonatoDesativado = campeonato.get();
            campeonatoDesativado.setAtivo(false);
            return campeonatoRepository.save(campeonatoDesativado);
        }else {
            throw new RegistroNaoEncontradoException("Registro de apoio a saude nao encontrado!");
        }
    }

    private String gerarCodigoUnico() {
        String codigo;
        Random random = new Random();
        boolean codigoExiste;
        do {
            codigo = gerarCodigoAleatorio(random);
            codigoExiste = campeonatoRepository.existsByCodigo(codigo);
        } while (codigoExiste);
        return codigo;
    }

    private String gerarCodigoAleatorio(Random random) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            codigo.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return codigo.toString();
    }

}
