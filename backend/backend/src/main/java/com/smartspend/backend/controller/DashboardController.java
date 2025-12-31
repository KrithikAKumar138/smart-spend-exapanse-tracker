//package com.smartspend.backend.controller;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class DashboardController {
//    @GetMapping("/dashboard")
//    public String dashboard(Authentication auth, Model model) {
//        model.addAttribute("email", auth.getName());
//        model.addAttribute("roles", auth.getAuthorities());
//        return "dashboard";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage(Authentication auth, Model model) {
//        model.addAttribute("email", auth.getName());
//        return "admin";
//    }
//}
