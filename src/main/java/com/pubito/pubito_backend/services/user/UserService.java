package com.pubito.pubito_backend.services.user;

import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.dto.user.UserUpdateRequestDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser (UserRegisterRequestDTO userDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    void deleteUserByID(Long id);

    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO userDTO);

    void addRoleToUser(Long userId, String roleName);

    void removeRoleFromUser(Long userId, String roleName);

    UserResponseDTO blockUser(Long userId);

    UserResponseDTO unblockUser(Long userId);

}
