package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.entities.Bar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarMapper {

    BarResponseDTO bartoBarResponseDTO(Bar bar);

}
