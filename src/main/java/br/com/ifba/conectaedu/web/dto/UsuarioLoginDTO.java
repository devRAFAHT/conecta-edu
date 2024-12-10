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
public class UsuarioLoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8, max = 50, message = "A senha deve ter entre 8 e 50 caracteres.")
    private String password;
}