package com.pubito.pubito_backend.services.bar;

import com.pubito.pubito_backend.dto.bar.*;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.mappers.BarMapper;
import com.pubito.pubito_backend.repositories.BarRepository;
import com.pubito.pubito_backend.repositories.MenuRepository;
import com.pubito.pubito_backend.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BarServiceImpl implements BarService {

    private final BarRepository barRepository;
    private final BarMapper barMapper;
    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;


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
        if (!barRepository.existsById(id)) {
            throw new RuntimeException("bar not found");
        }
        barRepository.deleteById(id);
    }

    @Override
    public List<TrendyBarResponseDTO> getTrendyBarsFromLastWeek() {
        List<Object[]> rows = reviewRepository.getTrendyBarsLastWeek();

        return rows.stream()
                .map(row -> {
                    Long barId = ((Number) row[0]).longValue();
                    Long reviewsCount = ((Number) row[1]).longValue();

                    var barOpt = barRepository.findById(barId);
                    if (barOpt.isEmpty()) {
                        return null;
                    }

                    var bar = barOpt.get();

                    return new TrendyBarResponseDTO(
                            bar.getId(),
                            bar.getName(),
                            reviewsCount
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public BarResponseDTO uptadeBar(Long id, BarUpdateRequestDTO barDTO) {
        Bar bar = barRepository.findById(id).orElseThrow(() -> new RuntimeException("bar not found"));

        bar.setName(barDTO.name());
        bar.setDescription(barDTO.description());

        barRepository.save(bar);
        return barMapper.toDTO(bar);
    }

    @Override
    public List<BarMenuItemCountResponseDTO> getBarsByMenuItemsCount() {
        List<Object[]> rows = menuRepository.getBarsByMenuItemsCountDesc();

        return rows.stream()
                .map(row -> {
                    Long barId = ((Number) row[0]).longValue();
                    Long itemsCount = ((Number) row[1]).longValue();

                    var barOpt = barRepository.findById(barId);
                    if (barOpt.isEmpty()) {
                        return null;
                    }

                    var bar = barOpt.get();

                    return new BarMenuItemCountResponseDTO(
                            bar.getId(),
                            bar.getName(),
                            itemsCount
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<BarResponseDTO> searchBars(String city, String type, Float minAvgRate, String sortBy) {

        String normalizedCity = (city != null && !city.isBlank()) ? city.toLowerCase() : null;

        if ("price".equalsIgnoreCase(sortBy)) {
            List<Object[]> rows = barRepository.findByFiltersOrderByMinMenuPrice(
                    normalizedCity,
                    minAvgRate
            );

            return rows.stream()
                    .map(row -> (Bar) row[0])
                    .map(barMapper::toDTO)
                    .collect(Collectors.toList());
        }

        List<Bar> bars = barRepository.findByFiltersOrderByAvgRateDesc(
                normalizedCity,
                minAvgRate
        );

        return bars.stream()
                .map(barMapper::toDTO)
                .collect(Collectors.toList());
    }
}