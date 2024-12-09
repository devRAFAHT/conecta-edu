package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.Calendario;
import br.com.ifba.conectaedu.entity.Endereco;
import br.com.ifba.conectaedu.entity.Escola;
import br.com.ifba.conectaedu.exception.DatabaseException;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.exception.UniqueViolationException;
import br.com.ifba.conectaedu.repository.EscolaRepository;
import br.com.ifba.conectaedu.repository.projection.EscolaProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EscolaService {

    private final EscolaRepository repository;
    private final CalendarioService calendarioService;
    private final EnderecoService enderecoService;

    @Transactional
    public Escola create(Escola escola){
        log.info("Criando uma nova escola: {}", escola);

        boolean existsByName = repository.existsByNome(escola.getNome());
        if(existsByName){
            throw new UniqueViolationException("Já tem uma escola com esse nome.");
        }

        Calendario calendario = new Calendario();
        calendario.setInicioAnoLetivo(LocalDate.of(2024, 02, 10));
        calendario.setFinalAnoLetivo(LocalDate.of(2024, 12, 10));
        calendario = calendarioService.create(calendario);
        log.info("Calendário criado: {}", calendario);

        log.info("Calendario " + calendario.getId());

        Endereco endereco = enderecoService.create(escola.getEndereco());
        log.info("Endereço criado ou associado: {}", endereco);

        escola.setCalendario(calendarioService.findById(calendario.getId()));
        escola.setEndereco(enderecoService.findById(endereco.getId()));

        escola = repository.save(escola);
        log.info("Escola salva no banco de dados: {}", escola);
        return escola;
    }

    @Transactional(readOnly = true)
    public Page<EscolaProjection> findAll(Pageable pageable){
        log.info("Buscando todas as Escolas com paginação: página {}", pageable.getPageNumber());
        return repository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Escola findById(Long id) {
        log.info("Buscando escola com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Escola com ID {} não encontrada.", id);
                    return new ResourceNotFoundException("Escola com id " + id + " não encontrada.");
                });
    }

    @Transactional(readOnly = true)
    public Escola findByNome(String nome) {
        log.info("Buscando escola com nome: {}", nome);
        return repository.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Escola com nome '{}' não encontrada.", nome);
                    return new ResourceNotFoundException("Escola com nome '" + nome + "' não encontrada.");
                });
    }

    @Transactional
    public Escola update(Long id, Escola novaEscola) {
        log.info("Iniciando atualização da escola com ID: {}", id);

        Escola escola = findById(id);
        log.info("Escola encontrada: {}", escola);

        log.info("Atualizando os dados da escola com as novas informações: {}", novaEscola);

        Optional<Escola> verificarNome = repository.findByNome(novaEscola.getNome());
        if (verificarNome.isPresent() && !verificarNome.get().getId().equals(id)) {
            log.error("Já existe outra escola com o mesmo nome: {}", novaEscola.getNome());
            throw new UniqueViolationException("Já existe outra escola com este nome.");
        }
        escola.setNome(novaEscola.getNome());
        escola.setNivelEnsino(novaEscola.getNivelEnsino());
        escola.setQuantidadeAlunos(novaEscola.getQuantidadeAlunos());
        escola.setQuantidadeAprovados(novaEscola.getQuantidadeAprovados());
        escola.setQuantidadeEvadidos(novaEscola.getQuantidadeEvadidos());

        escola = repository.save(escola);
        log.info("Escola atualizada com sucesso: {}", escola);

        return escola;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Iniciando exclusão da escola com ID: {}", id);

        Escola escola = findById(id);
        log.info("Escola encontrada para exclusão: {}", escola);

        try {
            repository.delete(escola);
            log.info("Escola com ID {} excluída com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade ao tentar excluir a escola com ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Violação de integridade.");
        }
    }
}
