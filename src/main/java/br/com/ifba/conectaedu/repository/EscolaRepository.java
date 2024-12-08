package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.Escola;
import br.com.ifba.conectaedu.repository.projection.EscolaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {

    Optional<Escola> findByNome(String nome);

    @Query("select e from Escola e")
    Page<EscolaProjection> findAllPageable(Pageable pageable);
}
