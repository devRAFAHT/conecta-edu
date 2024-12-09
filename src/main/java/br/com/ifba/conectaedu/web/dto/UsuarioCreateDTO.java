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

    @Size(min = 1, max = 50, message = "O nome deve conter entre 1 e 50 caracteres.")
    @NotBlank(message = "O campo nome completo é obrigatório.")
    private String nomeCompleto;
    @NotBlank(message = "O campo username é obrigatório.")
    @Size(min = 1, max = 20, message = "O username deve conter entre 1 e 20 caracteres.")
    private String username;
    @NotBlank(message = "O campo senha é obrigatório.")
    @Size(min = 8, max = 50, message = "A senha deve ter entre 8 e 50 caracteres.")
    private String senha;
}