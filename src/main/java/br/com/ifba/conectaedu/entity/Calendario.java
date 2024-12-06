package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "calendario")
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate inicioAnoLetivo;
    private LocalDate finalAnoLetivo;

    @ManyToMany(mappedBy = "calendarios")
    @Setter(value = AccessLevel.NONE)
    private Set<ProgramaEducacional> programasEducacionais = new HashSet<>();

    @ManyToMany(mappedBy = "calendarios")
    @Setter(value = AccessLevel.NONE)
    private Set<EventoEscolar> eventosEscolares = new HashSet<>();

    @ManyToMany(mappedBy = "calendarios")
    @Setter(value = AccessLevel.NONE)
    private Set<Feriado> feriados = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendario that = (Calendario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
