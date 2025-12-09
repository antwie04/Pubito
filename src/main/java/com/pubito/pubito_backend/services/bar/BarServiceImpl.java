package com.pubito.pubito_backend.services.bar;

import com.pubito.pubito_backend.dto.bar.BarCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.BarResponseDTO;
import com.pubito.pubito_backend.dto.bar.BarUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.mappers.BarMapper;
import com.pubito.pubito_backend.repositories.BarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BarServiceImpl implements BarService{

    private final BarRepository barRepository;
    private final BarMapper barMapper;


    @Override
    public BarResponseDTO createBar(BarCreateRequestDTO barDTO) {
        Bar bar = Bar.builder()
                .name(barDTO.name())
                .description(barDTO.description())
                .build();

        barRepository.save(bar);
        return barMapper.toDTO(bar);
    }

    @Override
    public BarResponseDTO getBarById(Long id) {
        Bar bar = barRepository.findById(id).orElseThrow(() -> new RuntimeException("bar not found"));
        return barMapper.toDTO(bar);
    }

    @Override
    public List<BarResponseDTO> getAllBars() {
        return barRepository.findAll().stream()
                .map(barMapper::toDTO)
                .toList();

    }

    @Override
    public void deleteBarById(Long id) {
        if(!barRepository.existsById(id)){
            throw new RuntimeException("bar not found");
        }
        barRepository.deleteById(id);
    }

    @Override
    public BarResponseDTO uptadeBar(Long id, BarUpdateRequestDTO barDTO) {
        Bar bar = barRepository.findById(id).orElseThrow(() -> new RuntimeException("bar not found"));

        bar.setName(barDTO.name());
        bar.setDescription(barDTO.description());

        barRepository.save(bar);
        return barMapper.toDTO(bar);
    }
}
