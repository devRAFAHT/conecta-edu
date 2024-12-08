package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.Calendario;
import br.com.ifba.conectaedu.web.dto.CalendarioResponseDTO;
import br.com.ifba.conectaedu.web.dto.CalendarioUpdateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalendarioMapper {

    public static Calendario toEntity(CalendarioUpdateDTO dto) {
        return new ModelMapper().map(dto, Calendario.class);
    }

    public static CalendarioResponseDTO toDto(Calendario calendario) {
        return new ModelMapper().map(calendario, CalendarioResponseDTO.class);
    }

}
