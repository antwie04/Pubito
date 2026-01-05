package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.address.AddressUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Address;
import com.pubito.pubito_backend.entities.Bar;
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

    /**
     * Mapuje tylko pola (bez relacji).
     */
    public Address toEntity(AddressCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();
        address.setCity(dto.city());
        address.setGoogleMapsUrl(dto.googleMapsUrl());
        address.setStreet(dto.street());
        address.setLatitude(dto.latitude());
        address.setLongitude(dto.longitude());
        return address;
    }

    /**
     * Mapuje pola + ustawia relację do baru (żeby addresses.bar_id nie był NULL).
     */
    public Address toEntity(AddressCreateRequestDTO dto, Bar bar) {
        Address address = toEntity(dto);
        if (address != null) {
            address.setBar(bar);
        }
        return address;
    }

    /**
     * Update pól (bez ruszania relacji bar).
     */
    public void updateEntity(Address address, AddressUpdateRequestDTO dto) {
        if (address == null || dto == null) {
            return;
        }

        address.setCity(dto.city());
        address.setGoogleMapsUrl(dto.googleMapsUrl());
        address.setStreet(dto.street());
        address.setLatitude(dto.latitude());
        address.setLongitude(dto.longitude());
        // NIE DOTYKAJ address.setBar(...)
    }
}