package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.Feriado;
import br.com.ifba.conectaedu.web.dto.FeriadoCreateDTO;
import br.com.ifba.conectaedu.web.dto.FeriadoResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeriadoMapper {

    public static Feriado toFeriado(FeriadoCreateDTO dto) {
        return new ModelMapper().map(dto, Feriado.class);
    }

    public static FeriadoResponseDTO toDto(Feriado feriado) {
        return new ModelMapper().map(feriado, FeriadoResponseDTO.class);
    }

}
