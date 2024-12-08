package br.com.ifba.conectaedu.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDTO {

    @NotBlank(message = "Campo obrigatório.")
    private String nomeCompleto;
    @NotBlank(message = "Campo obrigatório.")
    @Email(message = "formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;
    @NotBlank(message = "Campo obrigatório.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String senha;
}