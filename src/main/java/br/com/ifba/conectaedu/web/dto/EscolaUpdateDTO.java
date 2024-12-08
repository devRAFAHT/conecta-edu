package br.com.ifba.conectaedu.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscolaUpdateDTO {

    private String nome;
    private Integer quantidadeAlunos;
    private Integer quantidadeEvadidos;
    private Integer quantidadeAprovados;
    private String nivelEnsino;

}
