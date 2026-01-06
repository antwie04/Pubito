package com.pubito.pubito_backend.services.bar;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.bar.*;
import com.pubito.pubito_backend.entities.Address;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.CompanyDetails;
import com.pubito.pubito_backend.entities.User;
import com.pubito.pubito_backend.mappers.AddressMapper;
import com.pubito.pubito_backend.mappers.BarMapper;
import com.pubito.pubito_backend.mappers.CompanyDetailsMapper;
import com.pubito.pubito_backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepository userRepository;
    private final CompanyDetailsRepository companyDetailsRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CompanyDetailsMapper companyDetailsMapper;



    @Override
    @Transactional
    public BarResponseDTO createBar(BarCreateRequestDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Bar bar = Bar.builder()
                .name(dto.name())
                .description(dto.description())
                .owner(owner)
                .build();

        bar = barRepository.save(bar);

        if (dto.address() != null) {
            Address address = addressMapper.toEntity(dto.address(), bar);
            bar.setAddress(address);

            addressRepository.save(address);
        }

        if (dto.companyDetails() != null) {
            CompanyDetails cd = companyDetailsMapper.toEntity(dto.companyDetails(), bar);

            cd.setBar(bar);

            cd = companyDetailsRepository.save(cd);

            bar.setCompanyDetails(cd);
        }

        bar = barRepository.save(bar);


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
    @Transactional
    public void deleteBarById(Long id) {
        Bar bar = barRepository.findById(id).orElseThrow(() -> new RuntimeException("bar not found"));

        if (bar.getAddress() != null) {
            Address address = bar.getAddress();
            bar.setAddress(null);
            addressRepository.delete(address);
        }

        if (bar.getCompanyDetails() != null) {
            CompanyDetails companyDetails = bar.getCompanyDetails();
            bar.setCompanyDetails(null);
            companyDetailsRepository.delete(companyDetails);
        }
        
        barRepository.delete(bar);
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
    @Transactional
    public BarResponseDTO uptadeBar(Long id, BarUpdateRequestDTO barDTO) {
        Bar bar = barRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("bar not found"));

        bar.setName(barDTO.name());
        bar.setDescription(barDTO.description());

        if (barDTO.address() != null) {
            if (bar.getAddress() == null) {
                bar.setAddress(addressMapper.toEntity(
                        new AddressCreateRequestDTO(
                                barDTO.address().city(),
                                barDTO.address().googleMapsUrl(),
                                barDTO.address().street(),
                                barDTO.address().latitude(),
                                barDTO.address().longitude()
                        )
                ));
            } else {
                addressMapper.updateEntity(bar.getAddress(), barDTO.address());
            }
        }

        if (barDTO.companyDetails() != null) {
            if (bar.getCompanyDetails() == null) {
                var cd = companyDetailsMapper.toEntity(
                        new com.pubito.pubito_backend.dto.companydetails.CompanyDetailsCreateRequestDTO(
                                barDTO.companyDetails().websiteUrl(),
                                barDTO.companyDetails().phoneNumber()
                        ),
                        bar
                );
                bar.setCompanyDetails(cd);
            } else {
                bar.getCompanyDetails().setBar(bar);

                companyDetailsMapper.updateEntity(bar.getCompanyDetails(), barDTO.companyDetails());
            }
        }

        Bar saved = barRepository.save(bar);
        return barMapper.toDTO(saved);
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

    @Override
    public List<String> getCities() {
        return barRepository.findDistinctCities();
    }
}