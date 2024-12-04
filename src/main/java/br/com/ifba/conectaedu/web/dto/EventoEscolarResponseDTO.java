package br.com.ifba.conectaedu.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoEscolarResponseDTO {

    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String periodo;
    private BigDecimal pontosParticipacao;
    private Integer cargaHoraria;

}
