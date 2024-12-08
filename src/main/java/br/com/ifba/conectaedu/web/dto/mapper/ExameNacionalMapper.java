package br.com.ifba.conectaedu.web.dto.mapper;


import br.com.ifba.conectaedu.entity.ExameNacional;
import br.com.ifba.conectaedu.web.dto.ExameNacionalCreateDTO;
import br.com.ifba.conectaedu.web.dto.ProgramaEducacionalCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExameNacionalMapper {

    public static ExameNacional toExameNacional(ProgramaEducacionalCreateDTO dto) {
        return new ModelMapper().map(dto, ExameNacional.class);
    }

    public static ExameNacionalCreateDTO toDto(ExameNacional exameNacional) {
        return new ModelMapper().map(exameNacional, ExameNacionalCreateDTO.class);
    }
}

