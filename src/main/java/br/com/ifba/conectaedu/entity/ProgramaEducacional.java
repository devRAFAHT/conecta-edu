package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "programas_educacionais")
public class ProgramaEducacional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String nome;
    @Column(length = 500, columnDefinition = "TEXT")
    private String descricao;
    @Column(nullable = false)
    private LocalDate dataInicio;
    @Column(nullable = false)
    private LocalDate dataTermino;
    @Column(nullable = false, length = 100)
    private String periodo;
    @Column(nullable = false)
    private Integer cargaHoraria;
    @Column(nullable = false, length = 100)
    private String nivelEnsino;

    @ManyToMany
    @JoinTable(
            name = "programa_calendario",
            joinColumns = @JoinColumn(name = "programa_id"),
            inverseJoinColumns = @JoinColumn(name = "calendario_id")
    )
    @Setter(value = AccessLevel.NONE)
    private Set<Calendario> calendarios = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramaEducacional that = (ProgramaEducacional) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
