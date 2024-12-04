package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.Feriado;
import br.com.ifba.conectaedu.repository.projection.FeriadoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FeriadoRepository extends JpaRepository<Feriado, Long> {

    @Query("select f from Feriado f")
    Page<FeriadoProjection> findAllPageable(Pageable pageable);
    //List<Feriado> findByCalendarioId(Long id);
    Optional<Feriado> findByNome(String nome);

}
