package com.pubito.pubito_backend.services.bar;

import com.pubito.pubito_backend.dto.bar.BarCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.mappers.BarMapper;
import com.pubito.pubito_backend.repositories.BarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarServiceImpl implements BarService{

    private final BarRepository barRepository;
    private final BarMapper barMapper;

    public BarServiceImpl(BarRepository barRepository, BarMapper barMapper) {
        this.barRepository = barRepository;
        this.barMapper = barMapper;
    }

    @Override
    public BarResponseDTO createBar(BarCreateRequestDTO barDTO) {
        Bar bar = Bar.builder()
                .name(barDTO.name())
                .description(barDTO.description())
                .build();

        barRepository.save(bar);
        return barMapper.bartoBarResponseDTO(bar);
    }

    @Override
    public BarResponseDTO getBarById(Long id) {
        return null;
    }

    @Override
    public List<BarResponseDTO> getAllBars() {
        return List.of();
    }

    @Override
    public void deleteBarById(Long id) {

    }

    @Override
    public BarResponseDTO uptadeBar(Long id, BarUpdateRequestDTO barDTO) {
        return null;
    }
}
