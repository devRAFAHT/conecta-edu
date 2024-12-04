package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {

    Optional<Escola> findByNome(String nome);

}
