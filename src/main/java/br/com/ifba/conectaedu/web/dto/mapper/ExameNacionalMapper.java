package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.ExameNacional;
import br.com.ifba.conectaedu.web.dto.ExameNacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.ExameNacionalResponseDTO;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalCreateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExameNacionalMapper {


    public static ExameNacional toExameNacional(ExameNacionalCreateDTO dto) {
        return new ModelMapper().map(dto, ExameNacional.class);
    }

    public static ExameNacionalResponseDTO toDto(ExameNacional exameNacional) {
        return new ModelMapper().map(exameNacional, ExameNacionalResponseDTO.class);
    }
}
