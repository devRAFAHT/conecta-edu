package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscolaUpdateDTO {

    @NotBlank(message = "O nome da escola é obrigatório.")
    @Size(min = 1, max = 50, message = "O nome deve conter entre 1 e 50 caracteres.")
    private String nome;
    @NotNull(message = "A quantidade de alunos é obrigatória.")
    @Positive(message = "A quantidade de alunos deve ser um número positivo.")
    private Integer quantidadeAlunos;
    private Integer quantidadeEvadidos;
    private Integer quantidadeAprovados;
    @NotBlank(message = "O nível de ensino é obrigatório.")
    private String nivelEnsino;

}
