package com.example.laba4.controller;

import com.example.laba4.model.Users;
import com.example.laba4.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Получить всех пользователей
    @GetMapping
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Создать нового пользователя
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersRepository.save(user);
    }

    // Обновить данные пользователя
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        return usersRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setDepartment(updatedUser.getDepartment());
                    return usersRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersRepository.deleteById(id);
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
