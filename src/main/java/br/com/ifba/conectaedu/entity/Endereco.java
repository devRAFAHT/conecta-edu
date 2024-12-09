package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 10)
    private String cep;
    @Column(nullable = false, length = 50)
    private String estado;
    @Column(nullable = false, length = 50)
    private String cidade;
    @Column(length = 50)
    private String bairro;
    @Column(nullable = false, length = 50)
    private String rua;
    @Column(nullable = false)
    private Integer numero;


    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL, orphanRemoval = true)
    private Escola escola;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
