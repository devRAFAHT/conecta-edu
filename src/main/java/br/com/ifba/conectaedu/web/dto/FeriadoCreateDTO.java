package br.com.ifba.conectaedu.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FeriadoCreateDTO {

    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
