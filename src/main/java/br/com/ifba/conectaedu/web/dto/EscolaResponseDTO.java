package br.com.ifba.conectaedu.web.dto;

import br.com.ifba.conectaedu.entity.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscolaResponseDTO {

    private Long id;
    private String nome;
    private Integer quantidadeAlunos;
    private Integer quantidadeEvadidos;
    private Integer quantidadeAprovados;
    private String nivelEnsino;
    private EnderecoResponseDTO endereco;

}
