package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Enums.Role;
import com.example.demo.SecurityConfigurations.DataInitializer;
import com.example.demo.entity.User;
import com.example.demo.repository.DealRepository;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DealRepository dealRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    private User dummyUser;

    @BeforeEach
    void setup() {
        dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setUsername("dummy");
        dummyUser.setRole(Role.ADMIN);
        dummyUser.setActive(true);

        // 🔥 LENIENT FIX (THIS IS THE KEY)
        lenient().when(passwordEncoder.encode(anyString()))
                 .thenReturn("encodedPwd");
    }

    // -------------------- USERS --------------------

    @Test
    void shouldCreateUsersWhenTheyDoNotExist() throws Exception {
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);

        // prevent deal creation
        when(dealRepository.count())
                .thenReturn(1L);

        dataInitializer.run();

        // EXACTLY 4 users
        verify(userRepository, times(4))
                .save(any(User.class));

        verify(dealRepository, never())
                .saveAll(any());
    }

    @Test
    void shouldNotCreateUsersIfTheyAlreadyExist() throws Exception {
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);

        when(dealRepository.count())
                .thenReturn(1L);

        dataInitializer.run();

        verify(userRepository, never())
                .save(any(User.class));
    }

    // -------------------- DEALS --------------------

    @Test
    void shouldCreateDealsWhenNoDealsExist() throws Exception {
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);

        when(dealRepository.count())
                .thenReturn(0L);

        // 🔥 Handles satwika, sweety, keerthi, aishu
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(dummyUser));

        dataInitializer.run();

        verify(dealRepository)
                .saveAll(any());

        verify(dealRepository, atLeastOnce())
                .save(any());
    }

    @Test
    void shouldNotCreateDealsIfDealsAlreadyExist() throws Exception {
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);

        when(dealRepository.count())
                .thenReturn(5L);

        dataInitializer.run();

        verify(dealRepository, never())
                .saveAll(any());
    }
}
