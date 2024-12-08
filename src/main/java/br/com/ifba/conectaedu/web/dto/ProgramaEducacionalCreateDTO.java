package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaEducacionalCreateDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres.")
    private String nome;
    @Size(max = 500, message = "A descrição deve conter no máximo 500 caracteres.")
    private String descricao;
    @NotNull(message = "O campo 'dataInicio' é obrigatório.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
    private LocalDate dataInicio;
    @NotNull(message = "O campo 'dataTermino' é obrigatório.")
    @FutureOrPresent(message = "A data de término deve ser hoje ou uma data futura.")
    private LocalDate dataTermino;
    @NotBlank(message = "O campo 'periodo' é obrigatório.")
    @Size(max = 100, message = "O período deve conter no máximo 100 caracteres.")
    private String periodo;
    @NotNull(message = "O campo 'cargaHoraria' é obrigatório.")
    @Positive(message = "A carga horária deve ser um número positivo.")
    private Integer cargaHoraria;
    @NotBlank(message = "O campo 'nivelEnsino' é obrigatório.")
    @Size(max = 100, message = "O nível de ensino deve conter no máximo 100 caracteres.")
    private String nivelEnsino;

}
