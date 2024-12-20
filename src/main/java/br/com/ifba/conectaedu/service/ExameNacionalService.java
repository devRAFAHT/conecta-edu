package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.ExameNacional;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.repository.ExameNacionalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExameNacionalService {

    @Autowired
    private final ExameNacionalRepository repository;

    @Transactional
    public ExameNacional create(ExameNacional exameNacional){
        log.info("Criando novo Exame com Nome {}", exameNacional.getNome());

        return repository.save(exameNacional);
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ExameNacional findByID(Long id){
        log.info("Buscando Exame com id {}", id);
        return repository.findById(Math.toIntExact(id)).orElseThrow(() -> {
            log.error("Exame com id {} Não Encontrado", id);
            return new ResourceNotFoundException("Exame com id " + id + " não encontrado");
        });
    }

    @Transactional
    public ExameNacional update(Long id, ExameNacional NovoexameNacional){
        log.info("Atualizando Exame com id {}", id);
        ExameNacional exameNacional = findByID(id);
        exameNacional.setNome(NovoexameNacional.getNome());
        exameNacional.setDescricao(NovoexameNacional.getDescricao());
        exameNacional.setDataExame(NovoexameNacional.getDataExame());
        exameNacional.setNivelEnsino(NovoexameNacional.getNivelEnsino());

        return repository.save(exameNacional);
    }

    @Transactional
    public void delete(long id){
        log.info("Deletando Exame com id {}", id);
        ExameNacional exameNacional = findByID(id);

        try{
            repository.delete(exameNacional);
            log.info("Exame com id {} deletado com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao tentar deletar o Exame com id {}", id, e);
            throw new ResourceNotFoundException("Erro ao tentar deletar o Exame com id " + id);
        }
    }
}
