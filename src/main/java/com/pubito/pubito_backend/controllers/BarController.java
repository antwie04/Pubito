package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.bar.BarCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarUpdateRequestDTO;
import com.pubito.pubito_backend.services.bar.BarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bars")
@RequiredArgsConstructor
public class BarController {

    private final BarService barService;

    @PostMapping
    public ResponseEntity<BarResponseDTO> createBar(@RequestBody BarCreateRequestDTO dto){
        return ResponseEntity.ok(barService.createBar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarResponseDTO> getBar(@PathVariable Long id){
        return ResponseEntity.ok(barService.getBarById(id));
    }

    @GetMapping
    public ResponseEntity<List<BarResponseDTO>> getAllBars(){
        return ResponseEntity.ok(barService.getAllBars());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarResponseDTO> updateBar(@PathVariable Long id, @RequestBody BarUpdateRequestDTO dto){
        return ResponseEntity.ok(barService.uptadeBar(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBar(@PathVariable Long id){
        barService.deleteBarById(id);
        return ResponseEntity.noContent().build();
    }

}
