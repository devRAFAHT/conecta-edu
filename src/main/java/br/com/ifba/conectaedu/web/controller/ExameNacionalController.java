package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.ExameNacional;
import br.com.ifba.conectaedu.service.ExameNacionalService;
import br.com.ifba.conectaedu.web.dto.ExameNacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.ExameNacionalResponseDTO;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.mapper.ExameNacionalMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/exame-nacional")
public class ExameNacionalController {

    @Autowired
    private final ExameNacionalService ExameService;

    @PostMapping
    public ResponseEntity<ExameNacionalResponseDTO> create(@Valid @RequestBody ExameNacionalCreateDTO dto){
        ExameNacional exameNacional = ExameNacionalMapper.toExameNacional(dto);
        ExameService.create(exameNacional);

        return ResponseEntity.status(201).body(ExameNacionalMapper.toDto(exameNacional));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameNacionalResponseDTO> findById(@PathVariable Long id){
        ExameNacional exameNacional = ExameService.findByID(id);
        return ResponseEntity.ok(ExameNacionalMapper.toDto(exameNacional));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameNacionalResponseDTO> update(@PathVariable Long id, @RequestBody ExameNacionalCreateDTO dto){
        ExameNacional exameNacional = ExameNacionalMapper.toExameNacional(dto);
        ExameService.update(id, exameNacional);
        return ResponseEntity.ok(ExameNacionalMapper.toDto(exameNacional));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ExameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
