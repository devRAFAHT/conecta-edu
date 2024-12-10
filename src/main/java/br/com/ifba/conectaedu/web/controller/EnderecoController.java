package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.Endereco;
import br.com.ifba.conectaedu.service.EnderecoService;
import br.com.ifba.conectaedu.web.dto.EnderecoCreateDTO;
import br.com.ifba.conectaedu.web.dto.EnderecoResponseDTO;
import br.com.ifba.conectaedu.web.dto.mapper.EnderecoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/endereco")
public class EnderecoController {

    private final EnderecoService service;

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> create(@Valid @RequestBody EnderecoCreateDTO dto){
        Endereco endereco = EnderecoMapper.toEndereco(dto);
        service.create(endereco);
        return ResponseEntity.status(201).body(EnderecoMapper.toDto(endereco));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Long id){
        Endereco endereco = service.findById(id);
        return ResponseEntity.ok(EnderecoMapper.toDto(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> update(@Valid @PathVariable Long id, @RequestBody EnderecoCreateDTO dto){
        Endereco endereco = EnderecoMapper.toEndereco(dto);
        service.update(id, endereco);
        return ResponseEntity.ok(EnderecoMapper.toDto(endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
