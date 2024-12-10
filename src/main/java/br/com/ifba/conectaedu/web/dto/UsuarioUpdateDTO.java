package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioUpdateDTO {

    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String senhaAtual;
    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String novaSenha;
    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String confirmaSenha;

}
