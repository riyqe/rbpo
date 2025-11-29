package com.example.laba4.service;

import com.example.laba4.repository.UsersRepository;
import com.example.laba4.model.Users;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class LoadUser implements UserDetailsService { // Убираем вложенность

    private final UsersRepository repo;

    public LoadUser(UsersRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }
}
