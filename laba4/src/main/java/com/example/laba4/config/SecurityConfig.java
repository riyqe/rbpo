package com.example.laba4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Configuration
public class SecurityConfig {

    // Бин, который будет шифровать пароли.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .authorizeHttpRequests(auth -> auth
                        // для всех
                        .requestMatchers("/api/auth/**").authenticated()
                        .requestMatchers("/api/csrf").authenticated()

                        // админское
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")

                        // админ может создавать категории
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/executors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/sla").hasRole("ADMIN")

                        // админ может вручную эскалировать
                        .requestMatchers(HttpMethod.POST, "/api/tickets/escalate").hasRole("ADMIN")

                        // админ видит список просроченных
                        .requestMatchers(HttpMethod.GET, "/api/tickets/overdue").hasRole("ADMIN")

                        // админ может привязать SLA
                        .requestMatchers(HttpMethod.POST, "/api/tickets/*/sla/*").hasRole("ADMIN")

                        // обновление/удаление категорий, исполнителей, SLA
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/executors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/executors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/sla/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/sla/**").hasRole("ADMIN")

                        // все авторизованные
                        .requestMatchers(HttpMethod.GET, "/api/tickets/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/executors/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/sla/**").authenticated()

                        // создание и работа с тикетами для всех авторизованных
                        .requestMatchers(HttpMethod.POST, "/api/tickets").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/tickets/*/assign/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/tickets/*/resolve").authenticated()

                        // остальное запрещено
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults()); // вкл Basic Auth

        return http.build();
    }
}

@RestController
class CsrfTokenController {

    @GetMapping("/api/csrf")
    public CsrfToken csrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }
}