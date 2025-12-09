package com.pubito.pubito_backend.services.user;

import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.dto.user.UserUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Role;
import com.pubito.pubito_backend.entities.User;
import com.pubito.pubito_backend.mappers.UserMapper;
import com.pubito.pubito_backend.repositories.RoleRepository;
import com.pubito.pubito_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDTO registerUser(UserRegisterRequestDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = User.builder()
                .email(userDTO.email())
                .password(passwordEncoder.encode(userDTO.password()))
                .nickname(userDTO.nickname())
                .isActive(true)
                .build();

        Role defaultRole = roleRepository.findByRoleName("USER")
                        .orElseThrow(() -> new RuntimeException("role not found"));
        user.getRoles().add(defaultRole);

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
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

        return userMapper.toDTO(user);
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException());

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(Long userId, String roleName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Override
    public UserResponseDTO blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        user.setActive(false);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        user.setActive(true);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
