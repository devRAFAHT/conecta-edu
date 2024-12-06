package br.com.ifba.conectaedu.web.controller;


import br.com.ifba.conectaedu.entity.ExameNacional;
import br.com.ifba.conectaedu.service.ExameNacionalService;
import br.com.ifba.conectaedu.web.dto.ExameNacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.ExameNacionalResponseDTO;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.mapper.ExameNacionalMapper;
import br.com.ifba.conectaedu.web.dto.mapper.ProgramaEducacionalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/exame-nacional")
public class ExameNacionalController {
    
    private final ExameNacionalService ExameService;

    @PostMapping
    public ResponseEntity<ExameNacionalCreateDTO> create(@RequestBody ExameNacionalCreateDTO dto){
        ExameNacional exameNacional = ExameNacionalMapper.toExameNacional(dto);
        ExameService.create(exameNacional);
        return ResponseEntity.status(201).body(ExameNacionalMapper.toDto(exameNacional));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExameNacionalCreateDTO> findById(@PathVariable Long id){
        ExameNacional exameNacional = ExameService.findByID(id);
        return ResponseEntity.ok(ExameNacionalMapper.toDto(exameNacional));
    }
}
