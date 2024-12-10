package br.com.ifba.conectaedu.repository;

import br.com.ifba.conectaedu.entity.Administrador;
import br.com.ifba.conectaedu.repository.projection.AdministradorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    @Query("SELECT a FROM Administrador a WHERE a.escola.id = :escolaId")
    Page<AdministradorProjection> findByEscolaIdPageable(Long escolaId, Pageable pageable);
    List<Administrador> findByEscolaId(Long id);
    Administrador findByUsuarioId(Long id);
}
