package com.pubito.pubito_backend.services.address;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.address.AddressUpdateRequestDTO;

import java.util.List;

public interface AddressService {

    AddressResponseDTO createAddress(Long barId,AddressCreateRequestDTO dto);

    AddressResponseDTO getAddressById(Long id);

    List<AddressResponseDTO> getAllAddresses();

    AddressResponseDTO updateAddress(Long id, AddressUpdateRequestDTO dto);

    void deleteAddressById(Long id);
}
