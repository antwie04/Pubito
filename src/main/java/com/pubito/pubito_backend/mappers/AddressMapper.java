package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.address.AddressUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponseDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressResponseDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getGoogleMapsUrl(),
                address.getLatitude(),
                address.getLongitude()
        );
    }

    public Address toEntity(AddressCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();
        address.setCity(dto.city());
        address.setGoogleMapsUrl(dto.googleMapsUrl());
        return address;
    }

    public void updateEntity(Address address, AddressUpdateRequestDTO dto) {
        if (address == null || dto == null) {
            return;
        }

        address.setCity(dto.city());
        address.setGoogleMapsUrl(dto.googleMapsUrl());
    }
}
