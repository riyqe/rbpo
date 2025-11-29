package com.example.laba5.repository;

import com.example.laba5.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByRefreshToken(String refreshToken);
}