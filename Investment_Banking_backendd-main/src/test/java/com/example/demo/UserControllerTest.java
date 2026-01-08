package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.UserController;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getCurrentUser_success() {
        UserDTO userDTO = new UserDTO();
        when(userService.getCurrentUser()).thenReturn(userDTO);

        ResponseEntity<UserDTO> response =
                userController.getCurrentUser();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }
}
