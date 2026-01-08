package com.example.demo;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.SecurityConfigurations.JWTUtils;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilsTest {

    private JWTUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JWTUtils();
        // Using reflection to set private fields
        java.lang.reflect.Field secretField;
        java.lang.reflect.Field expirationField;
        try {
            secretField = JWTUtils.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtUtils, "MySuperSecretKeyForJWTs1234567890123456");

            expirationField = JWTUtils.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtUtils, 1000L * 60 * 60); // 1 hour
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerateAndValidateToken() {
        UserDetails user = User.withUsername("testuser").password("password").authorities(Collections.emptyList()).build();
        String token = jwtUtils.generateToken(user);

        assertNotNull(token);
        assertEquals("testuser", jwtUtils.extractUsername(token));
        assertTrue(jwtUtils.validateToken(token, user));
    }

    


    @Test
    void testExtractClaims() {
        UserDetails user = User.withUsername("claims").password("password").authorities(Collections.emptyList()).build();
        String token = jwtUtils.generateToken(user);

        assertEquals("claims", jwtUtils.extractUsername(token));
        assertNotNull(jwtUtils.extractExpiration(token));
    }
}
