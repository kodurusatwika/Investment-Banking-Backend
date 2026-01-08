package com.example.demo;



import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_ShouldReturnUser() {
        User user = new User();
        user.setUsername("user1");
        user.setActive(true);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        UserDetails loadedUser = userDetailsService.loadUserByUsername("user1");

        assertEquals("user1", loadedUser.getUsername());
    }

    @Test
    void loadUserByUsername_InactiveUser_ShouldThrowException() {
        User user = new User();
        user.setUsername("user1");
        user.setActive(false);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("user1"));
    }

    @Test
    void loadUserById_ShouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDetails loadedUser = userDetailsService.loadUserById(1L);

        assertEquals(1L, ((User) loadedUser).getId());
    }

    @Test
    void loadUserById_InactiveUser_ShouldThrowException() {
        User user = new User();
        user.setId(1L);
        user.setActive(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(ResourceNotFoundException.class, () -> userDetailsService.loadUserById(1L));
    }
}
