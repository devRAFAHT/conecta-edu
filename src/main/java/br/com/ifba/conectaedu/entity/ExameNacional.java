package br.com.ifba.conectaedu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exame_nacional")
public class ExameNacional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dataExame;

    @Column(nullable = false, length = 100)
    private String nome;
    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, length = 50)
    private String nivelEnsino;


    /*@ManyToOne
    @JoinColumn(name = "escola_id", nullable = false)
    private Escola escola;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExameNacional examenacional = (ExameNacional) o;
        return Objects.equals(id, examenacional.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
