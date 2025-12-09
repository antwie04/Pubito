package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.address.AddressUpdateRequestDTO;
import com.pubito.pubito_backend.services.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/bar/{barId}")
    public ResponseEntity<AddressResponseDTO> createAddress(@PathVariable Long barId, @RequestBody AddressCreateRequestDTO dto){
        AddressResponseDTO responseDTO = addressService.createAddress(barId ,dto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id){
        AddressResponseDTO responseDTO = addressService.getAddressById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses(){
        List<AddressResponseDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long id, @RequestBody AddressUpdateRequestDTO dto){
        AddressResponseDTO responseDTO = addressService.updateAddress(id, dto);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id){
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }


}
