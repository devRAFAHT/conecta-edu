package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.Administrador;
import br.com.ifba.conectaedu.web.dto.AdministradorResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdministradorMapper {

    public static Administrador toEntity(AdministradorResponseDTO dto) {
        return new ModelMapper().map(dto, Administrador.class);
    }

    public static AdministradorResponseDTO toDto(Administrador administrador) {
        return new ModelMapper().map(administrador, AdministradorResponseDTO.class);
    }
}
