package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BarMapper {

    private final AddressMapper addressMapper;
    private final CompanyDetailsMapper companyDetailsMapper;

    public BarResponseDTO toDTO(Bar bar) {
        if (bar == null) {
            return null;
        }

        AddressResponseDTO addressDTO = addressMapper.toDTO(bar.getAddress());
        var companyDetailsDTO = companyDetailsMapper.toDTO(bar.getCompanyDetails());

        return new BarResponseDTO(
                bar.getId(),
                bar.getName(),
                bar.getDescription(),
                addressDTO,
                companyDetailsDTO,
                (float) bar.getAvgRate(),
                bar.getOwner().getId()
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