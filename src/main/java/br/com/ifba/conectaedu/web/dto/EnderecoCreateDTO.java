package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EnderecoCreateDTO {

        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 12345-678.")
        @NotBlank(message = "O CEP é obrigatório.")
        private String cep;
        @NotBlank(message = "O estado é obrigatório.")
        @Size(min = 3, max = 50, message = "O nome do estado deve conter entre 3 e 50 caracteres")
        private String estado;
        @NotBlank(message = "A cidade é obrigatória.")
        @Size(min = 3, max = 50, message = "O nome da cidade deve conter entre 3 e 50 caracteres")
        private String cidade;
        @Size(max = 50, message = "O nome do bairro deve conter no máximo 50 caracteres")
        private String bairro;
        @NotBlank(message = "A rua é obrigatória.")
        @Size(min = 3, max = 50, message = "O nome da rua deve conter entre 3 e 50 caracteres")
        private String rua;
        @Positive(message = "O número deve ser um valor positivo.")
        private Integer numero;
}
