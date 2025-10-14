package com.example.labadva.controller;

import com.example.labadva.model.Users;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final List<Users> users = new ArrayList<>();

    @PostMapping
    public Users createUsers(@RequestBody Users user) {
        users.add(user);
        return user;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return users;
    }
}
