package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    //private String password;
}
