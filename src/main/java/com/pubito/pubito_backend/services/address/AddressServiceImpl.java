package com.pubito.pubito_backend.services.address;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.address.AddressUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Address;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.mappers.AddressMapper;
import com.pubito.pubito_backend.repositories.AddressRepository;
import com.pubito.pubito_backend.repositories.BarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final BarRepository barRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponseDTO createAddress(Long barId, AddressCreateRequestDTO dto) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new RuntimeException("bar not found"));

        Address address = new Address();
        address.setCity(dto.city());
        address.setGoogleMapsUrl(dto.googleMapsUrl());

        addressRepository.save(address);
        bar.setAddress(address);
        barRepository.save(bar);

        return addressMapper.toDTO(address);
    }

    @Override
    public AddressResponseDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("address not found"));
        return addressMapper.toDTO(address);
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDTO)
                .toList();
    }

    @Override
    public AddressResponseDTO updateAddress(Long id, AddressUpdateRequestDTO dto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("address not found"));

        address.setCity(dto.city());
        address.setGoogleMapsUrl(dto.googleMapsUrl());

        Address updated = addressRepository.save(address);
        return addressMapper.toDTO(updated);
    }

    @Override
    public void deleteAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("address not found"));

        barRepository.findAll().stream()
                .filter(bar -> bar.getAddress() != null && bar.getAddress().getId().equals(id))
                .forEach(bar -> {
                    bar.setAddress(null);
                    barRepository.save(bar);
                });

        addressRepository.delete(address);
    }
}
