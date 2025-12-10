package com.pubito.pubito_backend.services.bar;

import com.pubito.pubito_backend.dto.bar.*;

import java.util.List;

public interface BarService {

    BarResponseDTO createBar(BarCreateRequestDTO barDTO);

    BarResponseDTO getBarById(Long id);

    List<BarResponseDTO> getAllBars();

    void deleteBarById(Long id);

    List<TrendyBarResponseDTO> getTrendyBarsFromLastWeek();

    BarResponseDTO uptadeBar(Long id, BarUpdateRequestDTO barDTO);

    List<BarMenuItemCountResponseDTO> getBarsByMenuItemsCount();
}
