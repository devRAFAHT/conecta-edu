package br.com.ifba.conectaedu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "eventos_escolares")
public class EventoEscolar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String nome;
    @Column(nullable = false)
    private LocalDate dataInicio;
    @Column(nullable = false)
    private LocalDate dataTermino;
    @Column(nullable = false, length = 100)
    private String periodo;
    private BigDecimal pontosParticipacao;
    @Column(nullable = false)
    private Integer cargaHoraria;

    @ManyToMany
    @JoinTable(
            name = "evento_calendario",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "calendario_id")
    )
    @Setter(value = AccessLevel.NONE)
    private Set<Calendario> calendarios = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoEscolar that = (EventoEscolar) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
