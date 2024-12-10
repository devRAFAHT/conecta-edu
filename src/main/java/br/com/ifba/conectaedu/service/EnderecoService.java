package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.Endereco;
import br.com.ifba.conectaedu.exception.NotAdministratorException;
import br.com.ifba.conectaedu.exception.ResourceNotFoundException;
import br.com.ifba.conectaedu.repository.EnderecoRepository;
import br.com.ifba.conectaedu.exception.DatabaseException;
import br.com.ifba.conectaedu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EnderecoService {

    private final EnderecoRepository repository;
    private final UserUtil userUtil;

    @Transactional
     public Endereco create(Endereco endereco){
        log.info("Criando um novo endereço: {}", endereco);
        endereco.setEscola(null);
        endereco = repository.save(endereco);

        log.info("Endereço salvo com sucesso.");

        return endereco;
    }

    @Transactional(readOnly = true)
    public Endereco findById(Long id) {
        log.info("Buscando endereço com id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Endereço com id {} não encontrado", id);
                    return new ResourceNotFoundException("Endereço com id " + id + " não encontrado");
                });
    }

    @Transactional
    public Endereco update(Long id, Endereco novoEndereco){
        log.info("Atualizando endereço com id: {}", id);
        Endereco endereco = repository.findById(id).orElseThrow(() -> {
            log.error("Endereço com id {} não encontrado para atualização", id);
            return new ResourceNotFoundException("Endereço com id " + id + " não encontrado");
        });

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(endereco.getEscola());
        log.info("Usuário '{}' está tentando atualizar o endereço da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), endereco.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        log.debug("Atualizando campos do endereço para: {}", novoEndereco);
        endereco.setCep(novoEndereco.getCep());
        endereco.setEstado(novoEndereco.getEstado());
        endereco.setCidade(novoEndereco.getCidade());
        endereco.setBairro(novoEndereco.getBairro());
        endereco.setRua(novoEndereco.getRua());
        endereco.setNumero(novoEndereco.getNumero());

        endereco = repository.save(endereco);
        log.info("Endereço atualizado com sucesso: {}", endereco);

        return endereco;
    }

    @Transactional
    public void delete(Long id){
        log.info("Deletando endereço com id: {}", id);
        Endereco endereco = repository.findById(id).orElseThrow(() -> {
            log.error("Endereço com id {} não encontrado para exclusão", id);
            return new ResourceNotFoundException("Endereço com id " + id + " não encontrado");
        });

        try {
            repository.delete(endereco);
            log.info("Endereço com id {} deletado com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao deletar endereço com id {}. Violação de integridade.", id, e);
            throw new DatabaseException("Violação de integridade.");
        }
    }
}