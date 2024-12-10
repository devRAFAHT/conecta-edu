package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscolaCreateDTO {

    @NotBlank(message = "O nome da escola é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres.")
    private String nome;
    @NotNull(message = "A quantidade de alunos é obrigatória.")
    @Positive(message = "A quantidade de alunos deve ser um número positivo.")
    private Integer quantidadeAlunos;
    private Integer quantidadeEvadidos;
    private Integer quantidadeAprovados;
    @NotBlank(message = "O nível de ensino é obrigatório.")
    private String nivelEnsino;
    @NotNull(message = "O endereço é obrigatório.")
    @Valid
    private EnderecoCreateDTO endereco;

}
