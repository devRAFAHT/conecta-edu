package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.EventoEscolar;
import br.com.ifba.conectaedu.entity.Feriado;
import br.com.ifba.conectaedu.web.dto.EventoEscolarCreateDTO;
import br.com.ifba.conectaedu.web.dto.EventoEscolarResponseDTO;
import br.com.ifba.conectaedu.web.dto.FeriadoCreateDTO;
import br.com.ifba.conectaedu.web.dto.FeriadoResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventoEscolarMapper {

    public static EventoEscolar toEventoEscolar(EventoEscolarCreateDTO dto) {
        return new ModelMapper().map(dto, EventoEscolar.class);
    }

    public static EventoEscolarResponseDTO toDto(EventoEscolar eventoEscolar) {
        return new ModelMapper().map(eventoEscolar,EventoEscolarResponseDTO.class);
    }

}
