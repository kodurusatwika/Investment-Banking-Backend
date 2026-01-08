package com.example.demo.service;




import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.SecurityConfigurations.JWTUtils;
import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
  //Handles login  Authenticates credentials Generates JWT token
    public TokenDTO authenticate(AuthDTO authDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authDTO.getUsername(),
                authDTO.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(user);
        
        return new TokenDTO(jwt, user.getUsername(), user.getRole().name());
    }
}