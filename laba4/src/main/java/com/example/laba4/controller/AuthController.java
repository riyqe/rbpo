package com.example.laba4.controller;

import com.example.laba4.model.Users;
import com.example.laba4.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Логин уже занят!", HttpStatus.BAD_REQUEST);
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            return new ResponseEntity<>("Пароль должен быть не менее 8 символов.", HttpStatus.BAD_REQUEST);
        }

        // Шифруем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Устанавливаем роль по умолчанию
        user.setRole("ROLE_USER");

        usersRepository.save(user);

        return new ResponseEntity<>("Пользователь успешно зарегистрирован!", HttpStatus.CREATED);
    }
}
