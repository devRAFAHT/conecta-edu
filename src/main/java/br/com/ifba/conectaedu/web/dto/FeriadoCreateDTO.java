package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FeriadoCreateDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve conter entre 3 e 100 caracteres.")
    private String nome;
    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
    private LocalDate dataInicio;
    @NotNull(message = "A data de término é obrigatória.")
    @FutureOrPresent(message = "A data de término deve ser hoje ou uma data futura.")
    private LocalDate dataFim;

}
