package br.com.ifba.conectaedu.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExameNacionalCreateDTO {

    private String nome;
    private String descricao;
    private LocalDate dataAplicacao;
    private String nivelEnsino;

}
