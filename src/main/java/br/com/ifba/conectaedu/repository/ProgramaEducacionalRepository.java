package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.ProgramaEducacional;
import br.com.ifba.conectaedu.repository.projection.ProgramaEducacionalProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProgramaEducacionalRepository extends JpaRepository<ProgramaEducacional, Long> {

    @Query("select p from ProgramaEducacional p")
    Page<ProgramaEducacionalProjection> findAllPageable(Pageable pageable);
    Optional<ProgramaEducacional> findByNome(String nome);

    boolean existsByNome(String nome);
}
