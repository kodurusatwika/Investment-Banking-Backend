package com.example.demo;

import com.example.demo.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleResourceNotFound() {
        ResponseEntity<?> response =
                handler.handleResourceNotFound(new ResourceNotFoundException("Not found"));

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testHandleUnauthorized() {
        ResponseEntity<?> response =
                handler.handleUnauthorized(new UnauthorizedException("Unauthorized"));

        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    void testHandleValidation() {
        ResponseEntity<?> response =
                handler.handleValidation(new ValidationException("Invalid"));

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testHandleIllegalArgument() {
        ResponseEntity<?> response =
                handler.handleIllegalArgument(new IllegalArgumentException("Illegal"));

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testHandleBadCredentials() {
        ResponseEntity<?> response =
                handler.handleBadCredentials(new BadCredentialsException("Bad creds"));

        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    void testHandleAccessDenied() {
        ResponseEntity<?> response =
                handler.handleAccessDenied(new AccessDeniedException("Denied"));

        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void testHandleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("obj", "field", "must not be empty");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(error));

        ResponseEntity<Map<String, String>> response =
                handler.handleValidationExceptions(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("must not be empty", response.getBody().get("field"));
    }

    @Test
    void testHandleGeneralException() {
        ResponseEntity<?> response =
                handler.handleGeneralException(new Exception("Boom"));

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void testCustomExceptions() {
        assertEquals("Not found", new ResourceNotFoundException("Not found").getMessage());
        assertEquals("Unauthorized", new UnauthorizedException("Unauthorized").getMessage());
        assertEquals("Invalid", new ValidationException("Invalid").getMessage());
    }
}
