package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.Feriado;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.repository.FeriadoRepository;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeriadoService {

    private final FeriadoRepository repository;

    public Feriado create(Feriado feriado){
        return repository.save(feriado);
    }

    public Page<FeriadoProjection> findAll(Pageable pageable){
        return repository.findAllPageable(pageable);
    }

    public Feriado findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Feriado com id {} não encontrado", id);
                    return new ResourceNotFoundException("Feriado com id " + id + " não encontrado");
                });
    }

    public Feriado findByName(String nome) {
        return repository.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Feriado com nome {} não encontrado", nome);
                    return new ResourceNotFoundException("Feriado com nome " + nome + " não encontrado");
                });
    }


    public Feriado update(Long id, Feriado novoFeriado){
        Feriado feriado = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feriado com id " + id + " não encontrado"));

        feriado.setNome(novoFeriado.getNome());
        feriado.setDataInicio(novoFeriado.getDataInicio());
        feriado.setDataFim(novoFeriado.getDataFim());

        return repository.save(feriado);
    }

    public void delete(Long id){
        Feriado feriado = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feriado com nome " + id + " não encontrado"));

        try {
            repository.delete(feriado);
        }catch (DataIntegrityViolationException e){
            throw new ResourceNotFoundException("Violação de integridade");
        }
    }
}
