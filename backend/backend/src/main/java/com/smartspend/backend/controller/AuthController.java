package com.smartspend.backend.controller;
import com.smartspend.backend.dto.LoginRequest;
import com.smartspend.backend.dto.RegisterRequest;
import com.smartspend.backend.module.User;
import com.smartspend.backend.service.UserService;
import com.smartspend.backend.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        boolean created = userService.createUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        if (!created) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletResponse response) {

        if (!userService.login(request.getEmail(), request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String role = userService.getRoleByEmail(request.getEmail());
        System.out.println("ROLE = " + role);
        String token = JwtUtil.generateToken(request.getEmail(), role);

        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("JWT", "");
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        return ResponseEntity.ok("Logged out");
//    }
    public class LogoutController {

        @PostMapping("/api/auth/logout")
        public void logout(HttpServletResponse response) {

            Cookie cookie = new Cookie("JWT", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // true in production (https)
            cookie.setPath("/");
            cookie.setMaxAge(0); // 🔥 delete cookie

            response.addCookie(cookie);
        }


    }}



