package com.example.hostel.controller;

import com.example.hostel.model.User;
import com.example.hostel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //  Home Page (index)
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";  // loads templates/index.html
    }

    // Login Page
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null)
            model.addAttribute("error", "Invalid username or password");
        return "login";  // loads templates/login.html
    }

    //  Signup Page (GET)
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";  // loads templates/signup.html
    }

    //  Signup Form Submission (POST)
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute @Valid User user, @RequestParam String role) {
        if (userService.exists(user.getUsername())) {
            return "redirect:/signup?exists";
        }
        userService.register(user, role);
        return "redirect:/login?registered";  // after signup, redirect to login
    }

    //  Dashboard Redirect (Role-Based)
    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return "redirect:/login?error=NoRole";
        }

        // Get first role safely
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        switch (role) {
            case "ROLE_ADMIN":
                return "redirect:/admin/dashboard";
            case "ROLE_STUDENT":
                return "redirect:/student/dashboard";
            case "ROLE_CLEANER":
                return "redirect:/cleaner/dashboard";
            default:
                return "redirect:/login?error=UnknownRole";
        }
    }

    //  Optional Default Redirect (after login success)
    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication == null)
            return "redirect:/login";

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        switch (role) {
            case "ROLE_ADMIN":
                return "redirect:/admin/dashboard";
            case "ROLE_STUDENT":
                return "redirect:/student/dashboard";
            case "ROLE_CLEANER":
                return "redirect:/cleaner/dashboard";
            default:
                return "redirect:/login?error";
        }
    }
}
