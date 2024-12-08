package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "feriados")
public class Feriado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @ManyToMany
    @JoinTable(
            name = "feriado_calendario",
            joinColumns = @JoinColumn(name = "feriado_id"),
            inverseJoinColumns = @JoinColumn(name = "calendario_id")
    )
    @Setter(value = AccessLevel.NONE)
    private Set<Calendario> calendarios = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feriado feriado = (Feriado) o;
        return Objects.equals(id, feriado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
