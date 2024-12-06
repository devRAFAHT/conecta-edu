package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "exame_nacional")
public class ExameNacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Relacionamento
    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_escola", referencedColumnName = "id")
    private Escola escola;*/

    private String nome;
    private String descricao;
    private LocalDate dataAplicacao;
    private String nivelEnsino;


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
