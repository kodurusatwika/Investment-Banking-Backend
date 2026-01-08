package com.example.demo;

import com.example.demo.Enums.Role;
import com.example.demo.SecurityConfigurations.JWTUtils;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Ensure a real SecurityContext
        SecurityContextHolder.setContext(new org.springframework.security.core.context.SecurityContextImpl());
    }

    @Test
    void authenticate_ShouldReturnTokenDTO() {
        AuthDTO authDTO = new AuthDTO("user1", "password");

        User user = new User();
        user.setUsername("user1");
        user.setRole(Role.USER);  // Must not be null

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateToken(user)).thenReturn("mock-jwt-token");

        TokenDTO tokenDTO = authService.authenticate(authDTO);

        assertNotNull(tokenDTO);
        assertEquals("mock-jwt-token", tokenDTO.getToken());
        assertEquals("user1", tokenDTO.getUsername());

        // Verify authenticationManager called correctly
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        // Verify SecurityContext updated
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void authenticate_InvalidCredentials_ShouldThrowException() {
        AuthDTO authDTO = new AuthDTO("user1", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authService.authenticate(authDTO));

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void authenticate_JWTGenerationCalled() {
        AuthDTO authDTO = new AuthDTO("user1", "password");

        User user = new User();
        user.setUsername("user1");
        user.setRole(Role.USER);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateToken(user)).thenReturn("mock-jwt-token");

        authService.authenticate(authDTO);

        // Verify JWTUtils called exactly once
        verify(jwtUtils, times(1)).generateToken(user);
    }
}
