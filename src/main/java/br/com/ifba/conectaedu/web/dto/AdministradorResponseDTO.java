package br.com.ifba.conectaedu.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorResponseDTO {
    private Long usuarioId;
    private Long escolaId;
}
