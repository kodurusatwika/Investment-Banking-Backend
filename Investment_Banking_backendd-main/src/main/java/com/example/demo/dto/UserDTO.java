package com.example.demo.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.example.demo.Enums.Role;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Boolean active;
    private LocalDateTime createdAt;
}