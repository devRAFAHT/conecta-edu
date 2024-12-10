package br.com.ifba.conectaedu.service;

import br.com.ifba.conectaedu.entity.*;
import br.com.ifba.conectaedu.exception.*;
import br.com.ifba.conectaedu.repository.CalendarioRepository;
import br.com.ifba.conectaedu.repository.projection.EventoEscolarProjection;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import br.com.ifba.conectaedu.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarioService {

    private final CalendarioRepository repository;
    private final FeriadoService feriadoService;
    private final ProgramaEducacionalService programaEducacionalService;
    private final EventoEscolarService eventoEscolarService;
    private final UserUtil userUtil;

    @Transactional
    public Calendario create(Calendario calendario) {
        log.info("Iniciando a criação de um novo calendário.");

        calendario = repository.save(calendario);

        log.info("Calendário salvo com sucesso no banco de dados: {}", calendario);
        return calendario;
    }


    @Transactional(readOnly = true)
    public Calendario findById(Long id){
        log.info("Buscando calendário com id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Calendário com id {} não encontrado", id);
                    return new ResourceNotFoundException("Calendário com id " + id + " não encontrado");
                });
    }

    @Transactional
    public Calendario update(Long id, Calendario novoCalendario) {
        log.info("Iniciando atualização do calendário com ID: {}", id);

        Calendario calendario = findById(id);
        log.info("Calendário encontrado: {}", calendario);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar a escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), id, isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        calendario.setInicioAnoLetivo(novoCalendario.getInicioAnoLetivo());
        calendario.setFinalAnoLetivo(novoCalendario.getFinalAnoLetivo());
        log.info("Dados atualizados para: início do ano letivo = {}, final do ano letivo = {}",
                novoCalendario.getInicioAnoLetivo(), novoCalendario.getFinalAnoLetivo());

        if (calendario.getInicioAnoLetivo().isAfter(calendario.getFinalAnoLetivo())) {
            log.error("Validação falhou: a data de início ({}) é posterior à data de término ({})",
                    calendario.getInicioAnoLetivo(), calendario.getFinalAnoLetivo());
            throw new DateValidationException("A data de início não pode ser posterior à data de término.");
        }

        Calendario savedCalendario = repository.save(calendario);
        log.info("Calendário atualizado e salvo com sucesso: {}", savedCalendario);

        return savedCalendario;
    }


    @Transactional
    public void delete(Long id){
        Calendario calendario = findById(id);

        try {
            repository.delete(calendario);
            log.info("Calendário com id {} deletado com sucesso", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao deletar calendário com id {}. Violação de integridade.", id, e);
            throw new DatabaseException("Violação de integridade.");
        }
    }

    @Transactional
    public void adicionarFeriado(Long calendarioId, Long feriadoId) {
        log.info("Iniciando o processo de adicionar feriado ao calendário.");

        Calendario calendario = findById(calendarioId);
        log.debug("Calendário encontrado: {}", calendario);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar o calendário da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), calendario.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        Feriado feriado = feriadoService.findById(feriadoId);
        log.debug("Feriado encontrado: {}", feriado);

        if (calendario.getFeriados().contains(feriado)) {
            log.error("Tentativa de adicionar feriado que já existe no calendário.");
            throw new ItemAlreadyInCollectionException("O feriado já está no calendário");
        }

        calendario.getFeriados().add(feriado);
        log.debug("Feriado adicionado ao calendário: {}", feriado);

        if (feriado.getCalendarios().contains(calendario)) {
            log.error("Tentativa de associar calendário que já existe no feriado.");
            throw new ItemAlreadyInCollectionException("O feriado já está no calendário");
        }

        feriado.getCalendarios().add(calendario);
        log.debug("Calendário adicionado ao feriado: {}", calendario);

        feriadoService.adicionarCalendario(feriado, calendario);
        log.info("Chamada ao serviço adicionarCalendario concluída.");

        repository.save(calendario);
        log.info("Calendário salvo no repositório com sucesso.");
    }

    @Transactional
    public void adicionarProgramaEducacional(Long calendarioId, Long programaEducacionalId) {
        log.info("Iniciando o processo de adicionar programa educacional ao calendário.");

        Calendario calendario = findById(calendarioId);
        log.debug("Calendário encontrado: {}", calendario);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar o calendário da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), calendario.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        ProgramaEducacional programaEducacional = programaEducacionalService.findById(programaEducacionalId);
        log.debug("Programa educacional encontrado: {}", programaEducacional);

        if (calendario.getProgramasEducacionais().contains(programaEducacional)) {
            log.error("Tentativa de adicionar programa educacional que já existe no calendário.");
            throw new ItemAlreadyInCollectionException("O programa educacional já está no calendário");
        }

        calendario.getProgramasEducacionais().add(programaEducacional);
        log.debug("Programa educacional adicionado ao calendário: {}", programaEducacional);

        if (programaEducacional.getCalendarios().contains(calendario)) {
            log.error("Tentativa de associar calendário que já existe no programa educacional.");
            throw new ItemAlreadyInCollectionException("O programa educacional já está no calendário");
        }

        programaEducacional.getCalendarios().add(calendario);
        log.debug("Calendário adicionado ao programa educacional: {}", calendario);

        programaEducacionalService.adicionarCalendario(programaEducacional, calendario);
        log.info("Chamada ao serviço adicionarCalendario concluída.");

        repository.save(calendario);
        log.info("Calendário salvo no repositório com sucesso.");
    }

    @Transactional
    public void adicionarEventoEscolar(Long calendarioId, Long eventoEscolarId) {
        log.info("Iniciando o processo de adicionar evento escolar ao calendário.");

        Calendario calendario = findById(calendarioId);
        log.debug("Calendário encontrado: {}", calendario);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar o calendário da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), calendario.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        EventoEscolar eventoEscolar = eventoEscolarService.findById(eventoEscolarId);
        log.debug("Evento escolar encontrado: {}", eventoEscolar);

        if (calendario.getEventosEscolares().contains(eventoEscolar)) {
            log.error("Tentativa de adicionar evento escolar que já existe no calendário.");
            throw new ItemAlreadyInCollectionException("O evento escolar já está no calendário");
        }

        calendario.getEventosEscolares().add(eventoEscolar);
        log.debug("Evento escolar adicionado ao calendário: {}", eventoEscolar);

        if (eventoEscolar.getCalendarios().contains(calendario)) {
            log.error("Tentativa de associar calendário que já existe no evento escolar.");
            throw new ItemAlreadyInCollectionException("O evento escolar já está no calendário");
        }

        eventoEscolar.getCalendarios().add(calendario);
        log.debug("Calendário adicionado ao evento escolar: {}", calendario);

        eventoEscolarService.adicionarCalendario(eventoEscolar, calendario);
        log.info("Chamada ao serviço adicionarCalendario concluída.");

        repository.save(calendario);
        log.info("Calendário salvo no repositório com sucesso.");
    }

    @Transactional
    public void removerEventoEscolar(Long calendarioId, Long eventoEscolarId) {
        log.info("Iniciando remoção do evento escolar. Calendário ID: {}, Evento Escolar ID: {}", calendarioId, eventoEscolarId);

        Calendario calendario = findById(calendarioId);
        EventoEscolar eventoEscolar = eventoEscolarService.findById(eventoEscolarId);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar o calendário da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), calendario.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        if (!calendario.getEventosEscolares().contains(eventoEscolar)) {
            log.error("Tentativa de remover um evento escolar que não está no calendário. Evento Escolar ID: {}", eventoEscolarId);
            throw new ResourceNotFoundException("O evento escolar não está no calendário.");
        }

        log.info("Evento escolar encontrado no calendário. Procedendo com remoção.");

        calendario.getEventosEscolares().remove(eventoEscolar);

        eventoEscolarService.removerCalendario(eventoEscolar, calendario);

        repository.save(calendario);

        log.info("Evento escolar removido e alterações salvas no repositório com sucesso.");
    }

    @Transactional
    public void removerProgramaEducacional(Long calendarioId, Long programaEducacionalId) {
        log.info("Iniciando remoção do programa educacional. Calendário ID: {}, Programa Educacional ID: {}", calendarioId, programaEducacionalId);

        Calendario calendario = findById(calendarioId);
        ProgramaEducacional programaEducacional = programaEducacionalService.findById(programaEducacionalId);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar o calendário da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), calendario.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        if (!calendario.getProgramasEducacionais().contains(programaEducacional)) {
            log.error("Tentativa de remover um programa educacional que não está no calendário. Programa Educacional ID: {}", programaEducacionalId);
            throw new ResourceNotFoundException("O programa educacional não está no calendário.");
        }

        log.info("Programa educacional encontrado no calendário. Procedendo com remoção.");

        calendario.getProgramasEducacionais().remove(programaEducacional);

        programaEducacionalService.removerCalendario(programaEducacional, calendario);

        repository.save(calendario);

        log.info("Programa educacional removido e alterações salvas no repositório com sucesso.");
    }

    @Transactional
    public void removerFeriado(Long calendarioId, Long feriadoId) {
        log.info("Iniciando remoção do feriado. Calendário ID: {}, Feriado ID: {}", calendarioId, feriadoId);

        Calendario calendario = findById(calendarioId);
        Feriado feriado = feriadoService.findById(feriadoId);

        boolean isAdminiOfSchool = userUtil.isAdminOfSchool(calendario.getEscola());
        log.info("Usuário '{}' está tentando atualizar o calendário da escola com ID: {}. Verificação de administrador: {}",
                UserUtil.getLoggedInUsername(), calendario.getEscola().getId(), isAdminiOfSchool);

        if (!isAdminiOfSchool) {
            log.error("Tentativa não autorizada de atualizar a escola. Usuário: '{}'", UserUtil.getLoggedInUsername());
            throw new NotAdministratorException("O usuário '" + UserUtil.getLoggedInUsername() + "' não é administrador dessa escola.");
        }

        if (!calendario.getFeriados().contains(feriado)) {
            log.error("Tentativa de remover um feriado que não está no calendário. Feriado ID: {}", feriadoId);
            throw new ResourceNotFoundException("O feriado não está no calendário.");
        }

        log.info("Feriado encontrado no calendário. Procedendo com remoção.");

        calendario.getFeriados().remove(feriado);

        feriadoService.removerCalendario(feriado, calendario);

        repository.save(calendario);

        log.info("Feriado removido e alterações salvas no repositório com sucesso.");
    }

    @Transactional(readOnly = true)
    public Page<FeriadoProjection> findFeriados(Long calendarioId, Pageable pageable) {
        log.info("Buscando feriados para o calendário com id: {}", calendarioId);

        findById(calendarioId);

        return repository.findFeriadosByCalendarioId(calendarioId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<EventoEscolarProjection> findEventosEscolares(Long calendarioId, Pageable pageable) {
        log.info("Buscando eventos escolares para o calendário com id: {}", calendarioId);

        findById(calendarioId);

        return repository.findEventosEscolaresByCalendarioId(calendarioId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ProgramaEducacionalProjection> findProgramasEducacionais(Long calendarioId, Pageable pageable) {
        log.info("Buscando programas educacionais para o calendário com id: {}", calendarioId);

        findById(calendarioId);

        return repository.findProgramasEducacionaisByCalendarioId(calendarioId, pageable);
    }

}
