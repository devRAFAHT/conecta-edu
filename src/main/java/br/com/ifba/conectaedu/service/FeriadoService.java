package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.Calendario;
import br.com.ifba.conectaedu.entity.Feriado;
import br.com.ifba.conectaedu.exception.DatabaseException;
import br.com.ifba.conectaedu.exception.DateValidationException;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.exception.UniqueViolationException;
import br.com.ifba.conectaedu.repository.FeriadoRepository;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeriadoService {

    private final FeriadoRepository repository;

    @Transactional
    public Feriado create(Feriado feriado){
        log.info("Criando feriado: {}", feriado.getNome());

        if (repository.existsByNome(feriado.getNome())) {
            log.error("Já existe um feriado com o mesmo nome: {}", feriado.getNome());
            throw new UniqueViolationException("Já existe um feriado com este nome.");
        }

        if(feriado.getDataInicio().isAfter(feriado.getDataFim())){
            log.error("A data de início do feriado é posterior a data de término.");
            throw new DateValidationException("A data de início do feriado não pode ser posterior à data de término.");
        }

        return repository.save(feriado);
    }

    @Transactional(readOnly = true)
    public Page<FeriadoProjection> findAll(Pageable pageable){
        log.info("Buscando todos os feriados");
        return repository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Feriado findById(Long id) {
        log.info("Buscando feriado com id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Feriado com id {} não encontrado", id);
                    return new ResourceNotFoundException("Feriado com id " + id + " não encontrado");
                });
    }

    @Transactional(readOnly = true)
    public Feriado findByName(String nome) {
        log.info("Buscando feriado com nome {}", nome);
        return repository.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Feriado com nome {} não encontrado", nome);
                    return new ResourceNotFoundException("Feriado com nome " + nome + " não encontrado");
                });
    }

    @Transactional
    public Feriado update(Long id, Feriado novoFeriado){
        log.info("Atualizando feriado com id {}", id);
        Feriado feriado = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feriado com id " + id + " não encontrado"));

        Optional<Feriado> feriadoExistente = repository.findByNome(novoFeriado.getNome());
        if (feriadoExistente.isPresent() && !feriadoExistente.get().getId().equals(id)) {
            log.error("Já existe outro feriado com o mesmo nome: {}", novoFeriado.getNome());
            throw new UniqueViolationException("Já existe um feriado com este nome.");
        }

        feriado.setNome(novoFeriado.getNome());
        feriado.setDataInicio(novoFeriado.getDataInicio());
        feriado.setDataFim(novoFeriado.getDataFim());

        if(feriado.getDataInicio().isAfter(feriado.getDataFim())){
            log.error("A data de início do feriado é posterior a data de término.");
            throw new DateValidationException("A data de início do feriado não pode ser posterior à data de término.");
        }

        return repository.save(feriado);
    }

    @Transactional
    public void delete(Long id){
        log.info("Deletando feriado com id {}", id);
        Feriado feriado = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feriado com nome " + id + " não encontrado"));

        try {
            repository.delete(feriado);
        }catch (DataIntegrityViolationException e){
            log.error("Erro de violação de integridade ao tentar deletar o feriado com id {}", id);
            throw new DatabaseException("Violação de integridade");
        }
    }

    @Transactional
    public void adicionarCalendario(Feriado feriado, Calendario calendario) {
        feriado.getCalendarios().add(calendario);

        repository.save(feriado);
    }

    @Transactional
    public void removerCalendario(Feriado feriado, Calendario calendario) {
        feriado.getCalendarios().remove(calendario);

        repository.save(feriado);
    }
}