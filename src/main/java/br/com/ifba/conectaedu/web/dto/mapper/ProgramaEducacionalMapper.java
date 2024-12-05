package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.ProgramaEducacional;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgramaEducacionalMapper {

    public static ProgramaEducacional toProgramaEducacional(ProgramaEducacionalCreateDTO dto) {
        return new ModelMapper().map(dto, ProgramaEducacional.class);
    }

    public static ProgramaEducacionalResponseDTO toDto(ProgramaEducacional programaEducacional) {
        return new ModelMapper().map(programaEducacional,ProgramaEducacionalResponseDTO.class);
    }

}
