package br.com.ifba.conectaedu.web.dto;

import br.com.ifba.conectaedu.entity.Escola;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "A data do Exame é Obrigatório")
    private LocalDate dataExame;
    @NotBlank(message = "O nível de Ensino é obrigatório")
    private String nivelEnsino;


}
