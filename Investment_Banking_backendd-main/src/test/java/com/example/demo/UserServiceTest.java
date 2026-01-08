package com.example.demo;



import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnUserDTO() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("user1");
        dto.setEmail("user1@example.com");
        dto.setPassword("pass");

        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(userRepository.existsByEmail("user1@example.com")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("encoded-pass");

        User savedUser = new User();
        savedUser.setUsername("user1");
        savedUser.setEmail("user1@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO userDTO = userService.createUser(dto);

        assertNotNull(userDTO);
        assertEquals("user1", userDTO.getUsername());
    }

    @Test
    void createUser_ExistingUsername_ShouldThrowException() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("user1");

        when(userRepository.existsByUsername("user1")).thenReturn(true);

        assertThrows(ValidationException.class, () -> userService.createUser(dto));
    }
}
