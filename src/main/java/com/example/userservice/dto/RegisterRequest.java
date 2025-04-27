package com.example.userservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role; // 'student' or 'instructor'
}
