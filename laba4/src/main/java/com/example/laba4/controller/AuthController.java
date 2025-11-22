package com.example.laba4.controller;

import com.example.laba4.model.Users;
import com.example.laba4.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsersRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UsersRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {

        if (user.getPassword().length() < 8 ||
                !user.getPassword().matches(".*[!@#$%^&*()].*")) {
            return ResponseEntity.badRequest().body("Пароль слабый");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }

        repo.save(user);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }
}