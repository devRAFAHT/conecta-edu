package br.com.ifba.conectaedu.web.dto.mapper;

import br.com.ifba.conectaedu.entity.Endereco;
import br.com.ifba.conectaedu.web.dto.EnderecoCreateDTO;
import br.com.ifba.conectaedu.web.dto.EnderecoResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnderecoMapper {

    public static Endereco toEndereco(EnderecoCreateDTO dto) {
        return new ModelMapper().map(dto, Endereco.class);
    }

    public static EnderecoResponseDTO toDto(Endereco endereco) {
        return new ModelMapper().map(endereco, EnderecoResponseDTO.class);
    }
}
