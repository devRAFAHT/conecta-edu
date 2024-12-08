package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.ExameNacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExameNacionalRepository extends JpaRepository<ExameNacional, Long> {


}
