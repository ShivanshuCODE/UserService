package com.example.userservice.service;

import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> getProfile(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found"));
        }
        User user = userOpt.get();

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateProfile(Long userId, UserRequest updatedInfo) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User not found"));
        }
        User user = userOpt.get();
        if (updatedInfo.getName() != null  && !updatedInfo.getName().isBlank()) {
            user.setName(updatedInfo.getName());
        }
        if (updatedInfo.getEmail() != null && !updatedInfo.getEmail().isBlank()) {
            user.setEmail(updatedInfo.getEmail());
        }
        if (updatedInfo.getRole() != null && !updatedInfo.getRole().isBlank()) {
            user.setRole(updatedInfo.getRole());
        }
        if (updatedInfo.getPassword() != null && !updatedInfo.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedInfo.getPassword()));
        }
        userRepository.save(user);


        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(response);
    }

    public Long getCurrentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
