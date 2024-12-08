package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.*;
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
public class EventoEscolarCreateDTO {

    @NotBlank(message = "O nome do evento é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome do evento deve conter entre 3 e 50 caracteres.")
    private String nome;
    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
    private LocalDate dataInicio;
    @NotNull(message = "A data de término é obrigatória.")
    @FutureOrPresent(message = "A data de término deve ser hoje ou uma data futura.")
    private LocalDate dataTermino;
    @NotBlank(message = "O período é obrigatório.")
    @Size(max = 100, message = "O período deve conter no máximo 100 caracteres.")
    private String periodo;
    @NotNull(message = "Os pontos de participação são obrigatórios.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Os pontos devem ser no mínimo 0.0.")
    private BigDecimal pontosParticipacao;
    @NotNull(message = "A carga horária é obrigatória.")
    @Positive(message = "A carga horária deve ser um número positivo.")
    private Integer cargaHoraria;

}
