package com.example.authorization_demo.controller;

import com.example.authorization_demo.model.User;
import com.example.authorization_demo.payload.LoginRequest;
import com.example.authorization_demo.payload.RegisterRequest;
import com.example.authorization_demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        if (userService.usernameExists(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        return userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        boolean valid = userService.validateCredentials(
                request.getUsername(),
                request.getPassword()
        );

        if (!valid) {
            throw new RuntimeException("Invalid username or password!");
        }
        return "Login successful!";
    }
}
