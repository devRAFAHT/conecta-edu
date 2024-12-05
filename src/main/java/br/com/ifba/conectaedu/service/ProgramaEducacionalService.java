package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.ProgramaEducacional;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.repository.ProgramaEducacionalRepository;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProgramaEducacionalService {

    private final ProgramaEducacionalRepository repository;

    public ProgramaEducacional create(ProgramaEducacional programaEducacional) {
        log.info("Criando novo programa educacional com nome {}", programaEducacional.getNome());
        return repository.save(programaEducacional);
    }

    public Page<ProgramaEducacionalProjection> findAll(Pageable pageable) {
        log.info("Buscando todos os programas educacionais com paginação: página {}", pageable.getPageNumber());
        return repository.findAllPageable(pageable);
    }

    public ProgramaEducacional findById(Long id) {
        log.info("Buscando programa educacional com id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Programa educacional com id {} não encontrado", id);
                    return new ResourceNotFoundException("Programa Educacional com id " + id + " não encontrado");
                });
    }

    public ProgramaEducacional findByName(String nome) {
        log.info("Buscando programa educacional com nome {}", nome);
        return repository.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Programa educacional com nome {} não encontrado", nome);
                    return new ResourceNotFoundException("Programa Educacional com nome " + nome + " não encontrado");
                });
    }

    public ProgramaEducacional update(Long id, ProgramaEducacional programaAtualizado) {
        log.info("Atualizando programa educacional com id {}", id);
        ProgramaEducacional programaEducacional = findById(id);
        programaEducacional.setNome(programaAtualizado.getNome());
        programaEducacional.setDescricao(programaAtualizado.getDescricao());
        programaEducacional.setDataInicio(programaAtualizado.getDataInicio());
        programaEducacional.setDataTermino(programaAtualizado.getDataTermino());
        programaEducacional.setPeriodo(programaAtualizado.getPeriodo());
        programaEducacional.setCargaHoraria(programaAtualizado.getCargaHoraria());
        programaEducacional.setNivelEnsino(programaAtualizado.getNivelEnsino());
        return repository.save(programaEducacional);
    }

    public void delete(Long id) {
        log.info("Deletando programa educacional com id {}", id);
        ProgramaEducacional programaEducacional = findById(id);

        try {
            repository.delete(programaEducacional);
            log.info("Programa educacional com id {} deletado com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao tentar deletar o programa educacional com id {}", id, e);
            throw new ResourceNotFoundException("Erro ao tentar deletar o programa educacional com id " + id);
        }
    }
}
