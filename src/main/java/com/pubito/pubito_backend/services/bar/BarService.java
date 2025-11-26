package com.pubito.pubito_backend.services.bar;

import com.pubito.pubito_backend.dto.bar.BarCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarUpdateRequestDTO;

import java.util.List;

public interface BarService {

    BarResponseDTO createBar(BarCreateRequestDTO barDTO);

    BarResponseDTO getBarById(Long id);

    List<BarResponseDTO> getAllBars();

    void deleteBarById(Long id);

    BarResponseDTO uptadeBar(Long id, BarUpdateRequestDTO barDTO);
}
