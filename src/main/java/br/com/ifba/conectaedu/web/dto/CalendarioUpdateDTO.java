package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CalendarioUpdateDTO {

    @NotBlank(message = "A data de início do ano letivo é obrigatória.")
    private LocalDate inicioAnoLetivo;
    @NotBlank(message = "A data de final do ano letivo é obrigatória.")
    private LocalDate finalAnoLetivo;

}
