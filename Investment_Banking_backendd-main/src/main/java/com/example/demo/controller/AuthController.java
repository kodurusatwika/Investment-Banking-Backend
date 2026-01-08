package com.example.demo.controller;




import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody AuthDTO authDTO) {
        TokenDTO tokenDTO = authService.authenticate(authDTO);
        return ResponseEntity.ok(tokenDTO);
    }
}