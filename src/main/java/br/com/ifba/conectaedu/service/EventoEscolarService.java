package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.EventoEscolar;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.repository.EventoEscolarRepository;
import br.com.ifba.conectaedu.repository.projection.EventoEscolarProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventoEscolarService {

    private final EventoEscolarRepository repository;

    public Page<EventoEscolarProjection> findAll(Pageable pageable) {
        log.info("Buscando todos os eventos escolares com paginação: página {}", pageable.getPageNumber());
        return repository.findAllPageable(pageable);
    }

    public EventoEscolar findById(Long id) {
        log.info("Buscando evento escolar com id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Evento escolar com id {} não encontrado", id);
                    return new ResourceNotFoundException("Evento Escolar com id " + id + " não encontrado");
                });
    }

    public EventoEscolar findByName(String nome) {
        log.info("Buscando evento escolar com nome {}", nome);
        return repository.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Evento escolar com nome {} não encontrado", nome);
                    return new ResourceNotFoundException("Evento Escolar com nome " + nome + " não encontrado");
                });
    }

    public EventoEscolar create(EventoEscolar eventoEscolar) {
        log.info("Criando novo evento escolar com nome {}", eventoEscolar.getNome());
        return repository.save(eventoEscolar);
    }

    public EventoEscolar update(Long id, EventoEscolar eventoEscolarAtualizado) {
        log.info("Atualizando evento escolar com id {}", id);
        EventoEscolar eventoEscolar = findById(id);
        eventoEscolar.setNome(eventoEscolarAtualizado.getNome());
        eventoEscolar.setDataInicio(eventoEscolarAtualizado.getDataInicio());
        eventoEscolar.setDataTermino(eventoEscolarAtualizado.getDataTermino());
        eventoEscolar.setPeriodo(eventoEscolarAtualizado.getPeriodo());
        eventoEscolar.setPontosParticipacao(eventoEscolarAtualizado.getPontosParticipacao());
        eventoEscolar.setCargaHoraria(eventoEscolarAtualizado.getCargaHoraria());
        return repository.save(eventoEscolar);
    }

    public void delete(Long id) {
        log.info("Deletando evento escolar com id {}", id);
        EventoEscolar eventoEscolar = findById(id);

        try {
            repository.delete(eventoEscolar);
            log.info("Evento escolar com id {} deletado com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao tentar deletar o evento escolar com id {}", id, e);
            throw new ResourceNotFoundException("Evento escolar com id " + id + " não encontrado");
        }
    }
}