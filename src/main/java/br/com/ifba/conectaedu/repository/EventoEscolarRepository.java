package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.EventoEscolar;
import br.com.ifba.conectaedu.repository.projection.EventoEscolarProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventoEscolarRepository extends JpaRepository<EventoEscolar, Long> {

    @Query("select e from EventoEscolar e")
    Page<EventoEscolarProjection> findAllPageable(Pageable pageable);
    Optional<EventoEscolar> findByNome(String nome);

}
