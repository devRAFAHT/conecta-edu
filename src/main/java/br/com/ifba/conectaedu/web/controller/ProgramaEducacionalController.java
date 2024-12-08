package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.ProgramaEducacional;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import br.com.ifba.conectaedu.service.ProgramaEducacionalService;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalResponseDTO;
import br.com.ifba.conectaedu.web.dto.PageableDTO;
import br.com.ifba.conectaedu.web.dto.mapper.ProgramaEducacionalMapper;
import br.com.ifba.conectaedu.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/programa-educacional")
public class ProgramaEducacionalController {

    private final ProgramaEducacionalService service;

    @PostMapping
    public ResponseEntity<ProgramaEducacionalResponseDTO> create(@Valid @RequestBody ProgramaEducacionalCreateDTO dto) {
        ProgramaEducacional programaEducacional = ProgramaEducacionalMapper.toProgramaEducacional(dto);
        service.create(programaEducacional);
        return ResponseEntity.status(201).body(ProgramaEducacionalMapper.toDto(programaEducacional));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaEducacionalResponseDTO> findById(@PathVariable Long id) {
        ProgramaEducacional programaEducacional = service.findById(id);
        return ResponseEntity.ok(ProgramaEducacionalMapper.toDto(programaEducacional));
    }

    @GetMapping
    public ResponseEntity<PageableDTO> findAll(Pageable pageable) {
        Page<ProgramaEducacionalProjection> programasEducacionais = service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(programasEducacionais));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProgramaEducacionalResponseDTO> findByName(@PathVariable String nome) {
        ProgramaEducacional programaEducacional = service.findByName(nome);
        return ResponseEntity.ok(ProgramaEducacionalMapper.toDto(programaEducacional));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaEducacionalResponseDTO> update(@Valid @PathVariable Long id, @RequestBody ProgramaEducacionalCreateDTO dto) {
        ProgramaEducacional programaEducacional = ProgramaEducacionalMapper.toProgramaEducacional(dto);
        service.update(id, programaEducacional);
        return ResponseEntity.ok(ProgramaEducacionalMapper.toDto(programaEducacional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
