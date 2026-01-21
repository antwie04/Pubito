package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.user.UserRankingResponseDTO;
import com.pubito.pubito_backend.dto.user.UserRegisterRequestDTO;
import com.pubito.pubito_backend.dto.user.UserResponseDTO;
import com.pubito.pubito_backend.dto.user.UserUpdateRequestDTO;
import com.pubito.pubito_backend.services.review.ReviewService;
import com.pubito.pubito_backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

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

    @GetMapping("/by-email/{email:.+}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/{userId}/reviews/count")
    public ResponseEntity<Long> getReviewsCountForUser(@PathVariable Long userId){
        Long count = reviewService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<Void> addRoleToUser(@PathVariable Long userId,
                                              @PathVariable String roleName) {
        userService.addRoleToUser(userId, roleName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addRoleByEmail/{userEmail:.+}/roles/{roleName}")
    public ResponseEntity<Void> addRoleToUserByEmail(@PathVariable String userEmail,
                                              @PathVariable String roleName){
        userService.addRoleToUser(userEmail, roleName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long userId,
                                                   @PathVariable String roleName) {
        userService.removeRoleFromUser(userId, roleName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("ranking")
    public ResponseEntity<List<UserRankingResponseDTO>> getUsersRanking(){
        List<UserRankingResponseDTO> rankingResponseDTO = userService.getUsersRankingByReviews();
        return ResponseEntity.ok(rankingResponseDTO);
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<UserResponseDTO> blockUser(@PathVariable Long id){
        userService.blockUser(id);
        return ResponseEntity.ok(userService.blockUser(id));
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity<UserResponseDTO> unblockUser(@PathVariable Long id){
        userService.unblockUser(id);
        return ResponseEntity.ok(userService.unblockUser(id));
    }

}
