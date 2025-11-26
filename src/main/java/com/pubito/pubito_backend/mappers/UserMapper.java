package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserResponseDTO userToUserResponseDTO(User user);

}
