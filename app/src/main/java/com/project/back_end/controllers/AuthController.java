package com.project.back_end.controllers;

import com.project.back_end.dto.LoginRequest;
import com.project.back_end.dto.LoginResponse;
import com.project.back_end.models.User;
import com.project.back_end.models.UserRole;
import com.project.back_end.models.UserStatus;
import com.project.back_end.repo.UserRepository;
import com.project.back_end.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .message("Invalid username or password")
                            .build());
        }

        User userData = user.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), userData.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .message("Invalid username or password")
                            .build());
        }

        if (userData.getStatus() == UserStatus.SUSPENDED) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(LoginResponse.builder()
                            .message("Account is suspended")
                            .build());
        }

        String token = tokenService.generateToken(userData.getUsername(), userData.getRole().toString());

        return ResponseEntity.ok(LoginResponse.builder()
                .token(token)
                .username(userData.getUsername())
                .role(userData.getRole().toString())
                .userId(userData.getId())
                .message("Login successful")
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.builder()
                            .message("Username already exists")
                            .build());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.builder()
                            .message("Email already exists")
                            .build());
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(UserRole.PATIENT);

        User savedUser = userRepository.save(user);
        String token = tokenService.generateToken(savedUser.getUsername(), savedUser.getRole().toString());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LoginResponse.builder()
                        .token(token)
                        .username(savedUser.getUsername())
                        .role(savedUser.getRole().toString())
                        .userId(savedUser.getId())
                        .message("Registration successful")
                        .build());
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean isValid = tokenService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}
