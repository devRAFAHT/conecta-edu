package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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
