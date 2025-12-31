package com.smartspend.backend.service;

import com.smartspend.backend.module.User;
import com.smartspend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ✅ FIXED: return type boolean
    public boolean createUser(String name, String email, String rawPassword) {

        if (userRepository.findByEmail(email).isPresent()) {
            return false;
        }

        User user = new User(
                name,
                email,
                passwordEncoder.encode(rawPassword),
                "ROLE_USER"
        );

        userRepository.save(user);
        return true;
    }

    // 🔹 LOGIN LOGIC
    public boolean login(String email, String rawPassword) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
    public String getRoleByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRole)
                .orElse("ROLE_USER");
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public String getUserRole(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRole)
                .orElse("ROLE_USER");
    }
}

