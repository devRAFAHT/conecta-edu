package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.EventoEscolar;
import br.com.ifba.conectaedu.repository.projection.EventoEscolarProjection;
import br.com.ifba.conectaedu.service.EventoEscolarService;
import br.com.ifba.conectaedu.web.dto.EventoEscolarCreateDTO;
import br.com.ifba.conectaedu.web.dto.EventoEscolarResponseDTO;
import br.com.ifba.conectaedu.web.dto.PageableDTO;
import br.com.ifba.conectaedu.web.dto.mapper.EventoEscolarMapper;
import br.com.ifba.conectaedu.web.dto.mapper.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/evento-escolar")
public class EventoEscolarController {

    private final EventoEscolarService service;

    @PostMapping
    public ResponseEntity<EventoEscolarResponseDTO> create(@RequestBody EventoEscolarCreateDTO dto) {
        EventoEscolar eventoEscolar = EventoEscolarMapper.toEventoEscolar(dto);
        service.create(eventoEscolar);
        return ResponseEntity.status(201).body(EventoEscolarMapper.toDto(eventoEscolar));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoEscolarResponseDTO> findById(@PathVariable Long id) {
        EventoEscolar eventoEscolar = service.findById(id);
        return ResponseEntity.ok(EventoEscolarMapper.toDto(eventoEscolar));
    }

    @GetMapping
    public ResponseEntity<PageableDTO> findAll(Pageable pageable) {
        Page<EventoEscolarProjection> eventosEscolares = service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(eventosEscolares));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<EventoEscolarResponseDTO> findByName(@PathVariable String nome) {
        EventoEscolar eventoEscolar = service.findByName(nome);
        return ResponseEntity.ok(EventoEscolarMapper.toDto(eventoEscolar));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoEscolarResponseDTO> update(@PathVariable Long id, @RequestBody EventoEscolarCreateDTO dto) {
        EventoEscolar eventoEscolar = EventoEscolarMapper.toEventoEscolar(dto);
        service.update(id, eventoEscolar);
        return ResponseEntity.ok(EventoEscolarMapper.toDto(eventoEscolar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
