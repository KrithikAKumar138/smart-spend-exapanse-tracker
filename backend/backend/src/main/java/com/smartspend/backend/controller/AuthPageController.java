package com.smartspend.backend.controller;

import com.smartspend.backend.service.UserService;
//import com.smartspend.backend.util.JwtUtil;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class AuthPageController {
//    private final UserService userService;
//
//    public AuthPageController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // 🔹 LOGIN PAGE
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
//
//    // 🔹 REGISTER PAGE
//    @GetMapping("/register")
//    public String registerPage() {
//        return "register";
//    }
//    @PostMapping("/login-form")
//    public String loginUser(
//            @RequestParam String email,
//            @RequestParam String password,
//            HttpServletResponse response
//    ) {
//
//        if (!userService.login(email, password)) {
//            return "redirect:/login?error";
//        }
//
//        String role = userService.getRoleByEmail(email);
//        String token = JwtUtil.generateToken(email, role);
//
//        Cookie cookie = new Cookie("JWT", token);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60);
//
//        response.addCookie(cookie);
//
//        return "redirect:/dashboard";
//    }
//
//    // 🔹 HANDLE REGISTER FORM
//    @PostMapping("/register")
//    public String registerUser(
//            @RequestParam String name,
//            @RequestParam String email,
//            @RequestParam String password,
//            Model model
//    ) {
//        boolean success = userService.createUser(name, email, password);
//
//        if (!success) {
//            model.addAttribute("error", "Email already exists");
//            return "register";
//        }
//
//        model.addAttribute("success", "Registration successful. Please login.");
//        return "login";
//    }
//}