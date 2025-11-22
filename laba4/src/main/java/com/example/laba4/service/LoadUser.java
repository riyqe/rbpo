package com.example.laba4.service;

import com.example.laba4.repository.UsersRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

public class LoadUser {

    @Service
    public class LoadUsers implements UserDetailsService {

        private final UsersRepository repo;

        public LoadUsers(UsersRepository repo) {
            this.repo = repo;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            var user = repo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Not found"));

            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().replace("ROLE_", ""))
                    .build();
        }
    }

}
