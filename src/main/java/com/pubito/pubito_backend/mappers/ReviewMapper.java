package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.review.ReviewCreateRequestDTO;
import com.pubito.pubito_backend.dto.review.ReviewResponseDTO;
import com.pubito.pubito_backend.dto.review.ReviewUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.Review;
import com.pubito.pubito_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bar", source = "bar")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Review toEntity(ReviewCreateRequestDTO dto, Bar bar, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bar", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget Review review, ReviewUpdateRequestDTO dto);

    @Mapping(target = "barId", source = "bar.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewResponseDTO toDTO(Review review);
}
