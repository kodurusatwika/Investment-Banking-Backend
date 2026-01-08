package com.example.demo;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.AdminController;
import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void createUser_success() {
        CreateUserDTO input = new CreateUserDTO();
        UserDTO output = new UserDTO();

        when(userService.createUser(input)).thenReturn(output);

        ResponseEntity<UserDTO> response =
                adminController.createUser(input);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(output, response.getBody());
        verify(userService).createUser(input);
    }

    @Test
    void getAllUsers_success() {
        when(userService.getAllUsers()).thenReturn(List.of(new UserDTO()));

        ResponseEntity<List<UserDTO>> response =
                adminController.getAllUsers();

        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody().isEmpty());
        verify(userService).getAllUsers();
    }

    @Test
    void updateUserStatus_success() {
        UserDTO userDTO = new UserDTO();
        when(userService.updateUserStatus(1L, true)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response =
                adminController.updateUserStatus(1L, true);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }
}
