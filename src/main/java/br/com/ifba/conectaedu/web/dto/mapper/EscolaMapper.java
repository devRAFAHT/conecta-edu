package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.Escola;
import br.com.ifba.conectaedu.web.dto.EscolaCreateDTO;
import br.com.ifba.conectaedu.web.dto.EscolaResponseDTO;
import br.com.ifba.conectaedu.web.dto.EscolaUpdateDTO;
import org.modelmapper.ModelMapper;

public class EscolaMapper {

    public static Escola toEntity(EscolaCreateDTO dto) {
        return new ModelMapper().map(dto, Escola.class);
    }

    public static EscolaResponseDTO toDto(Escola escola) {
        return new ModelMapper().map(escola, EscolaResponseDTO.class);
    }

    public static Escola updateDTOToEntity(EscolaUpdateDTO dto){
        return new ModelMapper().map(dto, Escola.class);
    }

}
