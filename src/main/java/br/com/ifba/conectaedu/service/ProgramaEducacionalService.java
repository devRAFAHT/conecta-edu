package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.Calendario;
import br.com.ifba.conectaedu.entity.ProgramaEducacional;
import br.com.ifba.conectaedu.exception.DatabaseException;
import br.com.ifba.conectaedu.exception.DateValidationException;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.exception.UniqueViolationException;
import br.com.ifba.conectaedu.repository.ProgramaEducacionalRepository;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProgramaEducacionalService {

    private final ProgramaEducacionalRepository repository;

    @Transactional
    public ProgramaEducacional create(ProgramaEducacional programaEducacional) {
        log.info("Criando novo programa educacional com nome {}", programaEducacional.getNome());

        if (repository.existsByNome(programaEducacional.getNome())) {
            log.error("Já existe um programa educacional com o mesmo nome: {}", programaEducacional.getNome());
            throw new UniqueViolationException("Já existe um programa educacional com este nome.");
        }

        if(programaEducacional.getDataInicio().isAfter(programaEducacional.getDataTermino())){
            log.error("A data de início do programa educacional é posterior a data de término.");
            throw new DateValidationException("A data de início do programa educacional não pode ser posterior à data de término.");
        }

        return repository.save(programaEducacional);
    }


    @Transactional(readOnly = true)
    public Page<ProgramaEducacionalProjection> findAll(Pageable pageable) {
        log.info("Buscando todos os programas educacionais com paginação: página {}", pageable.getPageNumber());
        return repository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public ProgramaEducacional findById(Long id) {
        log.info("Buscando programa educacional com id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Programa educacional com id {} não encontrado", id);
                    return new ResourceNotFoundException("Programa Educacional com id " + id + " não encontrado");
                });
    }

    @Transactional(readOnly = true)
    public ProgramaEducacional findByName(String nome) {
        log.info("Buscando programa educacional com nome {}", nome);
        return repository.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Programa educacional com nome {} não encontrado", nome);
                    return new ResourceNotFoundException("Programa Educacional com nome " + nome + " não encontrado");
                });
    }

    @Transactional
    public ProgramaEducacional update(Long id, ProgramaEducacional programaAtualizado) {
        log.info("Atualizando programa educacional com id {}", id);

        Optional<ProgramaEducacional> programaExistente = repository.findByNome(programaAtualizado.getNome());
        if (programaExistente.isPresent() && !programaExistente.get().getId().equals(id)) {
            log.error("Já existe outro programa educacional com o mesmo nome: {}", programaAtualizado.getNome());
            throw new UniqueViolationException("Já existe um programa educacional com este nome.");
        }

        ProgramaEducacional programaEducacional = findById(id);
        programaEducacional.setNome(programaAtualizado.getNome());
        programaEducacional.setDescricao(programaAtualizado.getDescricao());
        programaEducacional.setDataInicio(programaAtualizado.getDataInicio());
        programaEducacional.setDataTermino(programaAtualizado.getDataTermino());
        programaEducacional.setPeriodo(programaAtualizado.getPeriodo());
        programaEducacional.setCargaHoraria(programaAtualizado.getCargaHoraria());
        programaEducacional.setNivelEnsino(programaAtualizado.getNivelEnsino());

        if(programaEducacional.getDataInicio().isAfter(programaEducacional.getDataTermino())){
            log.error("A data de início do programa educacional é posterior a data de término.");
            throw new DateValidationException("A data de início do programa educacional não pode ser posterior à data de término.");
        }

        return repository.save(programaEducacional);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deletando programa educacional com id {}", id);
        ProgramaEducacional programaEducacional = findById(id);

        try {
            repository.delete(programaEducacional);
            log.info("Programa educacional com id {} deletado com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao tentar deletar o programa educacional com id {}", id, e);
            throw new DatabaseException("Erro ao tentar deletar o programa educacional com id " + id);
        }
    }

    @Transactional
    public void adicionarCalendario(ProgramaEducacional programaEducacional, Calendario calendario) {
        programaEducacional.getCalendarios().add(calendario);

        repository.save(programaEducacional);
    }

    @Transactional
    public void removerCalendario(ProgramaEducacional programaEducacional, Calendario calendario) {
        programaEducacional.getCalendarios().remove(calendario);

        repository.save(programaEducacional);
    }
}
