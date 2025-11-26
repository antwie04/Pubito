package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.dto.user.UserUpdateRequestDTO;
import com.pubito.pubito_backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser (@RequestBody UserRegisterRequestDTO dto){
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO dto){
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserByID(id);
        return ResponseEntity.noContent().build();
    }
}
