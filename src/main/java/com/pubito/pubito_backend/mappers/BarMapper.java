package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.bar.BarCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import org.springframework.stereotype.Component;

@Component
public class BarMapper {

    public BarResponseDTO toDTO(Bar bar) {
        if (bar == null) {
            return null;
        }

        return new BarResponseDTO(
                bar.getId(),
                bar.getName(),
                bar.getDescription(),
                (float) bar.getAvgRate()
        );
    }


    public Bar toEntity(BarCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Bar bar = new Bar();
        bar.setName(dto.name());
        bar.setDescription(dto.description());
        bar.setAvgRate(0F);
        return bar;
    }

    public void updateEntity(Bar bar, BarUpdateRequestDTO dto) {
        if (bar == null || dto == null) {
            return;
        }

        bar.setName(dto.name());
        bar.setDescription(dto.description());
    }
}