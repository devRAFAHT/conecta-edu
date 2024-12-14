package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "escolas")
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String nome;
    @Column(nullable = false)
    private Integer quantidadeAlunos;
    private Integer quantidadeEvadidos;
    private Integer quantidadeAprovados;
    @Column(nullable = false)
    private String nivelEnsino;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_calendario", referencedColumnName = "id")
    private Calendario calendario;

    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    @Setter(value = AccessLevel.NONE)
    private Set<Administrador> administradores = new HashSet<>();

    /*@OneToMany(mappedBy = "escola", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExameNacional> exame;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Escola escola = (Escola) o;
        return Objects.equals(id, escola.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
