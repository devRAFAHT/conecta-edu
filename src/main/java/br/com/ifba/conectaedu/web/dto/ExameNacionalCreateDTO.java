package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExameNacionalCreateDTO {

    @NotBlank(message = "O nome do Exame é obrigatório")
    private String nome;
    @NotBlank(message = "Digite a descrição desse Exame")
    private String descricao;
    @NotBlank(message = "A data do Exame é Obrigatório")
    private LocalDate dataExame;
    @NotBlank(message = "O nível de Ensino é obrigatório")
    private String nivelEnsino;
}
