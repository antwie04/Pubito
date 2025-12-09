package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.entities.User;
import org.springframework.stereotype.Component;
import com.pubito.pubito_backend.entities.Role;

import java.util.List;

@Component
public class UserMapper {

     public UserResponseDTO toDTO(User user) {
          if (user == null) {
               return null;
          }
          List<String> roles = user.getRoles().stream()
                  .map(Role::getRoleName)
                  .toList();

          return new UserResponseDTO(
                  user.getId(),
                  user.getEmail(),
                  user.getNickname(),
                  user.isActive(),
                  roles
          );
     }

     public User toEntity(UserRegisterRequestDTO dto) {
          if (dto == null) {
               return null;
          }
          User user = new User();
          user.setEmail(dto.email());
          user.setPassword(dto.password());
          user.setNickname(dto.nickname());
          return user;
     }
}