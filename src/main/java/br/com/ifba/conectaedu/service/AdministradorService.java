package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.Administrador;
import br.com.ifba.conectaedu.entity.Escola;
import br.com.ifba.conectaedu.entity.Usuario;
import br.com.ifba.conectaedu.exception.*;
import br.com.ifba.conectaedu.repository.AdministradorRepository;
import br.com.ifba.conectaedu.repository.EscolaRepository;
import br.com.ifba.conectaedu.repository.projection.AdministradorProjection;
import br.com.ifba.conectaedu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdministradorService {

    private final AdministradorRepository repository;
    private final UsuarioService usuarioService;
    private final EscolaRepository escolaRepository;
    private final UserUtil userUtil;

    @Transactional
    public Administrador adicionarAdministradorNaEscola(Long usuarioId, Long escolaId) {
        log.info("Iniciando processo para criar administrador para a escola com usuárioId: {} e escolaId: {}", usuarioId, escolaId);

        log.info("Buscando escola com ID: {}", escolaId);
        Escola escola = escolaRepository.findById(escolaId)
                .orElseThrow(() -> {
                    log.error("Escola com ID {} não encontrada.", escolaId);
                    return new ResourceNotFoundException("Escola com id " + escolaId + " não encontrada.");
                });

        Usuario usuario = usuarioService.findById(usuarioId);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(escola);
        log.info("Usuário '{}' está tentando adicionar um administrador a escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), escola.getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de adicionar administrador a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        log.info("Escola encontrada: {}, Usuário encontrado: {}", escola, usuario);

        Administrador administradorExistente = repository.findByUsuarioId(usuarioId);

        if (administradorExistente != null && administradorExistente.getEscola() != null && administradorExistente.getEscola() != escola) {
            log.error("O usuário com ID {} já é administrador em uma escola: {}", usuarioId, administradorExistente.getEscola());
            throw new UserIsAlreadyAnAdministrator("O usuário já é administrador em outra escola.");
        }

        List<Administrador> administradoresEscola = repository.findByEscolaId(escolaId);
        if (administradoresEscola.contains(administradorExistente)) {
            log.error("O usuário com ID {} já é administrador dessa escola.", usuarioId);
            throw new UserIsAlreadyAnAdministrator("O usuário já é administrador dessa escola.");
        }

        Administrador novoAdministrador;

        if (administradorExistente == null) {
            novoAdministrador = new Administrador();
            novoAdministrador.setEscola(escola);
            novoAdministrador.setUsuario(usuario);
        } else {
            novoAdministrador = administradorExistente;
            novoAdministrador.setEscola(escola);
        }

        repository.save(novoAdministrador);

        log.info("Administrador associado à escola com sucesso: {}", novoAdministrador);

        return novoAdministrador;
    }


    @Transactional(readOnly = true)
    public Page<AdministradorProjection> findByEscola(Long escolaId, Pageable pageable) {
        log.info("Iniciando busca de administradores para a escola com ID: {} e parâmetros de paginação: {}", escolaId, pageable);

        Page<AdministradorProjection> resultado = repository.findByEscolaIdPageable(escolaId, pageable);

        log.info("Busca concluída com {} resultados.", resultado.getTotalElements());

        return resultado;
    }

    @Transactional(readOnly = true)
    public Administrador findById(Long id) {
        log.info("Buscando administrador com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Administrador com ID {} não encontrado.", id);
                    return new ResourceNotFoundException("Administrador com id " + id + " não encontrado.");
                });
    }

    @Transactional(readOnly = true)
    public Administrador findByUsuarioUsername(String username) {
        log.info("Buscando administrador com username: {}", username);
        return repository.findByUsuarioUsername(username)
                .orElseThrow(() -> {
                    log.error("Administrador com username '{}' não encontrado.", username);
                    return new ResourceNotFoundException("Administrador com username '" + username + "' não encontrado.");
                });
    }


    @Transactional
    public void removerAdministradorDaEscola(Long administradorId) {
        log.info("Iniciando a remoção do administrador com ID: {}", administradorId);

        Administrador administrador = findById(administradorId);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(administrador.getEscola());
        log.info("Usuário '{}' está tentando remover um administrador da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), administrador.getEscola(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de remover administrador da escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        if (administrador.getEscola() == null) {
            log.error("Administrador com ID: {} não está associado a nenhuma escola.", administradorId);
            throw new ResourceNotFoundException("Esse administrador não está associado a nenhuma escola.");
        }

        administrador.setEscola(null);

        repository.save(administrador);

        log.info("Administrador com ID: {} foi desvinculado com sucesso da sua escola.", administradorId);
    }


    @Transactional
    public void delete(Long id) {
        log.info("Iniciando exclusão do administrador com ID: {}", id);

        Administrador administrador = findById(id);

        try {
            log.info("Excluindo administrador: {}", administrador);
            repository.delete(administrador);
            log.info("Administrador com ID {} excluído com sucesso.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao tentar excluir administrador com ID {}: Violação de integridade.", id, e);
            throw new DatabaseException("Violação de integridade.");
        }
    }

    @Transactional
    public Administrador criarAdministrador(Administrador administrador){

        Long usuarioId = administrador.getUsuario().getId();
        Administrador administradorExistente = repository.findByUsuarioId(usuarioId);

        if (administradorExistente != null && administradorExistente.getEscola() != null) {
            log.error("O usuário com ID {} já é administrador em uma escola: {}", usuarioId, administradorExistente.getEscola());
            throw new UserIsAlreadyAnAdministrator("O usuário já é administrador em outra escola.");
        }

        return repository.save(administrador);
    }
}