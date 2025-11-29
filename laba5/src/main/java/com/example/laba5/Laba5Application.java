package com.example.laba5;

import com.example.laba5.model.Users;
import com.example.laba5.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Laba5Application {

    public static void main(String[] args) {
        SpringApplication.run(Laba5Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username}") String username,
            @Value("${app.admin.password}") String password,
            @Value("${app.admin.email}") String email
    ) {
        return args -> {
            if (usersRepository.findByUsername(username).isEmpty()) {

                Users admin = new Users();
                admin.setUsername(username);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRole("ROLE_ADMIN");
                admin.setEmail(email);
                admin.setDepartment("IT Department");

                usersRepository.save(admin);

                System.out.println(">>> Администратор создан из конфигурации. <<<");
            }
        };
    }
}