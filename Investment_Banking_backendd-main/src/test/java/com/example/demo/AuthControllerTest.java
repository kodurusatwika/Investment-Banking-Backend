package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.AuthController;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_success() {
        AuthDTO authDTO = new AuthDTO();
        TokenDTO tokenDTO = new TokenDTO();

        when(authService.authenticate(authDTO)).thenReturn(tokenDTO);

        ResponseEntity<TokenDTO> response =
                authController.login(authDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(tokenDTO, response.getBody());
        verify(authService).authenticate(authDTO);
    }
}
