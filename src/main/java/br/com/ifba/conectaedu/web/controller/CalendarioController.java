package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.entity.Calendario;
import br.com.ifba.conectaedu.repository.projection.EventoEscolarProjection;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import br.com.ifba.conectaedu.service.CalendarioService;
import br.com.ifba.conectaedu.web.dto.itemCalendarioDTO;
import br.com.ifba.conectaedu.web.dto.CalendarioResponseDTO;
import br.com.ifba.conectaedu.web.dto.CalendarioUpdateDTO;
import br.com.ifba.conectaedu.web.dto.mapper.CalendarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1/calendario")
public class CalendarioController {

    private final CalendarioService service;

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioResponseDTO> findById(@PathVariable Long id) {
        Calendario calendario = service.findById(id);
        return ResponseEntity.ok(CalendarioMapper.toDto(calendario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarioResponseDTO> update(@PathVariable Long id, @RequestBody CalendarioUpdateDTO dto) {
        Calendario novoCalendario = CalendarioMapper.toEntity(dto);
        Calendario atualizado = service.update(id, novoCalendario);
        return ResponseEntity.ok(CalendarioMapper.toDto(atualizado));
    }

    @PostMapping("/adicionar-feriado")
    public ResponseEntity<Void> adicionarFeriado(@RequestBody itemCalendarioDTO dto){
        service.adicionarFeriado(dto.getCalendarioId(), dto.getItemId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/adicionar-programa-educacional")
    public ResponseEntity<Void> adicionarProgramaEducacional(@RequestBody itemCalendarioDTO dto) {
        service.adicionarProgramaEducacional(dto.getCalendarioId(), dto.getItemId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/adicionar-evento-escolar")
    public ResponseEntity<Void> adicionarEventoEscolar(@RequestBody itemCalendarioDTO dto) {
        service.adicionarEventoEscolar(dto.getCalendarioId(), dto.getItemId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remover-feriado")
    public ResponseEntity<Void> removerFeriado(@RequestBody itemCalendarioDTO dto) {
        service.removerFeriado(dto.getCalendarioId(), dto.getItemId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remover-programa-educacional")
    public ResponseEntity<Void> removerProgramaEducacional(@RequestBody itemCalendarioDTO dto) {
        service.removerProgramaEducacional(dto.getCalendarioId(), dto.getItemId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remover-evento-escolar")
    public ResponseEntity<Void> removerEventoEscolar(@RequestBody itemCalendarioDTO dto) {
        service.removerEventoEscolar(dto.getCalendarioId(), dto.getItemId());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/feriados")
    public ResponseEntity<Page<FeriadoProjection>> getFeriados(@PathVariable Long id, Pageable pageable) {
        Page<FeriadoProjection> feriados = service.findFeriados(id, pageable);
        return ResponseEntity.ok(feriados);
    }

    @GetMapping("/{id}/eventos-escolares")
    public ResponseEntity<Page<EventoEscolarProjection>> getEventosEscolares(@PathVariable Long id, Pageable pageable) {
        Page<EventoEscolarProjection> eventos = service.findEventosEscolares(id, pageable);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}/programas-educacionais")
    public ResponseEntity<Page<ProgramaEducacionalProjection>> getProgramasEducacionais(@PathVariable Long id, Pageable pageable) {
        Page<ProgramaEducacionalProjection> programas = service.findProgramasEducacionais(id, pageable);
        return ResponseEntity.ok(programas);
    }

}
