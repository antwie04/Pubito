package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.user.LoginRequestDTO;
import com.pubito.pubito_backend.dto.user.LoginResponseDTO;
import com.pubito.pubito_backend.services.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        var auth = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        authenticationManager.authenticate(auth);

        String token = jwtService.generateToken(request.email());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
