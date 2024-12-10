package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.Feriado;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import br.com.ifba.conectaedu.service.FeriadoService;
import br.com.ifba.conectaedu.web.dto.FeriadoCreateDTO;
import br.com.ifba.conectaedu.web.dto.FeriadoResponseDTO;
import br.com.ifba.conectaedu.web.dto.PageableDTO;
import br.com.ifba.conectaedu.web.dto.mapper.FeriadoMapper;
import br.com.ifba.conectaedu.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/feriado")
public class FeriadoController {

    private final FeriadoService service;

    @PostMapping
    public ResponseEntity<FeriadoResponseDTO> create(@Valid @RequestBody FeriadoCreateDTO dto){
        Feriado feriado = FeriadoMapper.toFeriado(dto);
        service.create(feriado);
        return ResponseEntity.status(201).body(FeriadoMapper.toDto(feriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeriadoResponseDTO> findById(@PathVariable Long id){
        Feriado feriado = service.findById(id);
        return ResponseEntity.ok(FeriadoMapper.toDto(feriado));
    }

    @GetMapping
    public ResponseEntity<PageableDTO> findAll(Pageable pageable){
        Page<FeriadoProjection> feriados = service.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(feriados));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<FeriadoResponseDTO> findByName(@PathVariable String nome){
        Feriado feriado = service.findByName(nome);
        return ResponseEntity.ok(FeriadoMapper.toDto(feriado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeriadoResponseDTO> update(@Valid @PathVariable Long id, @RequestBody FeriadoCreateDTO dto){
        Feriado feriado = FeriadoMapper.toFeriado(dto);
        service.update(id, feriado);
        return ResponseEntity.ok(FeriadoMapper.toDto(feriado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
