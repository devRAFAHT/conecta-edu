package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.Calendario;
import br.com.ifba.conectaedu.repository.projection.EventoEscolarProjection;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Table(name = "calendarios")
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {

    @Query("SELECT f FROM Feriado f JOIN f.calendarios c WHERE c.id = :calendarioId")
    Page<FeriadoProjection> findFeriadosByCalendarioId(@Param("calendarioId") Long calendarioId, Pageable pageable);

    @Query("SELECT e FROM EventoEscolar e JOIN e.calendarios c WHERE c.id = :calendarioId")
    Page<EventoEscolarProjection> findEventosEscolaresByCalendarioId(@Param("calendarioId") Long calendarioId, Pageable pageable);

    @Query("SELECT p FROM ProgramaEducacional p JOIN p.calendarios c WHERE c.id = :calendarioId")
    Page<ProgramaEducacionalProjection> findProgramasEducacionaisByCalendarioId(@Param("calendarioId") Long calendarioId, Pageable pageable);
}
