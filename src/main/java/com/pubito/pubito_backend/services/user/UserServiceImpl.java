package com.pubito.pubito_backend.services.user;

import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.dto.user.UserUpdateRequestDTO;
import com.pubito.pubito_backend.entities.User;
import com.pubito.pubito_backend.mappers.UserMapper;
import com.pubito.pubito_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO registerUser(UserRegisterRequestDTO userDTO) {
        User user = new User();

        user.setEmail(user.getEmail());
        // tylko testowo
        user.setPassword(userDTO.password());

        userRepository.save(user);
        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper :: userToUserResponseDTO)
                .toList();
    }

    @Override
    public void deleteUserByID(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userDTO.email());
        userRepository.save(user);

        return userMapper.userToUserResponseDTO(user);
    }
}
