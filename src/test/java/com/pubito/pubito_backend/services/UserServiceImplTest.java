package com.pubito.pubito_backend.services;

import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.entities.Role;
import com.pubito.pubito_backend.entities.User;
import com.pubito.pubito_backend.mappers.UserMapper;
import com.pubito.pubito_backend.repositories.ReviewRepository;
import com.pubito.pubito_backend.repositories.RoleRepository;
import com.pubito.pubito_backend.repositories.UserRepository;
import com.pubito.pubito_backend.services.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUserWithEncodedPasswordAndDefaultRole() {
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO(
                "test@pubito.pl",
                "haslo",
                "User"
        );

        Role userRole = Role.builder().roleName("USER").build();
        UserResponseDTO responseDTO = new UserResponseDTO(
                1L, "test@pubito.pl", "User", true, List.of("USER")
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("ENCODED");
        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(userRole));
        when(userMapper.toDTO(any(User.class))).thenReturn(responseDTO);

        UserResponseDTO result = userService.registerUser(dto);

        assertEquals("test@pubito.pl", result.email());
        assertEquals("User", result.nickname());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();
        assertEquals("ENCODED", savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains(userRole));
        assertTrue(savedUser.isActive());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO(
                "test@pubito.pl",
                "haslo",
                "User"
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(dto)
        );

        assertEquals("user with this email already exists", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldBlockUser() {
        Long userId =1L;

        User user = User.builder()
                .id(userId)
                .email("test@pubito.pl")
                .password("haslo")
                .nickname("user")
                .isActive(true)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.blockUser(userId);

        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void shouldGetUserById(){
        Long userId = 1L;

        User user = User.builder()
                .id(userId)
                .email("test@pubito.pl")
                .password("secret")
                .nickname("Antoni")
                .isActive(true)
                .build();

        UserResponseDTO dto = new UserResponseDTO(
                userId,
                "test@pubito.pl",
                "Antoni",
                true,
                List.of()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(dto);

        UserResponseDTO result = userService.getUserById(userId);

        assertEquals(userId, result.id());
        assertEquals("test@pubito.pl", result.email());

        verify(userRepository).findById(userId);
        verify(userMapper).toDTO(user);


    }
}
