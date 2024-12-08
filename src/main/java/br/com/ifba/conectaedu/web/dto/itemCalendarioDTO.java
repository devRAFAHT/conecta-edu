package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class itemCalendarioDTO {

    @NotNull(message = "O campo 'calendarioId' é obrigatório.")
    @Positive(message = "O campo 'calendarioId' deve ser um número positivo.")
    private Long calendarioId;

    @NotNull(message = "O campo 'itemId' é obrigatório.")
    @Positive(message = "O campo 'itemId' deve ser um número positivo.")
    private Long itemId;

}
