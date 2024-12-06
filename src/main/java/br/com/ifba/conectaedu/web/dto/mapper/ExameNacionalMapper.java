package br.com.ifba.conectaedu.web.dto.mapper;


import br.com.ifba.conectaedu.entity.ExameNacional;
import br.com.ifba.conectaedu.web.dto.ExameNacionalCreateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExameNacionalMapper {

    public static ExameNacional toExameNacional(ExameNacionalCreateDTO dto) {
        return new ModelMapper().map(dto, ExameNacional.class);
    }

    public static ExameNacionalCreateDTO toDto(ExameNacional exameNacional) {
        return new ModelMapper().map(exameNacional, ExameNacionalCreateDTO.class);
    }
}
