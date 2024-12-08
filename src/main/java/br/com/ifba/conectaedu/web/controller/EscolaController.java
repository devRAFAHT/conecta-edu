package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.Escola;
import br.com.ifba.conectaedu.repository.projection.EscolaProjection;
import br.com.ifba.conectaedu.service.EscolaService;
import br.com.ifba.conectaedu.web.dto.EscolaCreateDTO;
import br.com.ifba.conectaedu.web.dto.EscolaResponseDTO;
import br.com.ifba.conectaedu.web.dto.EscolaUpdateDTO;
import br.com.ifba.conectaedu.web.dto.PageableDTO;
import br.com.ifba.conectaedu.web.dto.mapper.EscolaMapper;
import br.com.ifba.conectaedu.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/escola")
public class EscolaController {

    private final EscolaService service;

    @PostMapping
    public ResponseEntity<EscolaResponseDTO> create(@Valid @RequestBody EscolaCreateDTO dto) {
        Escola escola = EscolaMapper.toEntity(dto);
        escola = service.create(escola);
        return ResponseEntity.status(201).body(EscolaMapper.toDto(escola));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscolaResponseDTO> findById(@PathVariable Long id) {
        Escola escola = service.findById(id);
        return ResponseEntity.ok(EscolaMapper.toDto(escola));
    }

    @GetMapping
    public ResponseEntity<PageableDTO> findAll(Pageable pageable) {
        Page<EscolaProjection> escolas = service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(escolas));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<EscolaResponseDTO> findByNome(@PathVariable String nome) {
        Escola escola = service.findByNome(nome);
        return ResponseEntity.ok(EscolaMapper.toDto(escola));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscolaResponseDTO> update(@Valid @PathVariable Long id, @RequestBody EscolaUpdateDTO dto) {
        Escola escola = EscolaMapper.updateDTOToEntity(dto);
        escola = service.update(id, escola);
        return ResponseEntity.ok(EscolaMapper.toDto(escola));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
