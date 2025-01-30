package com.example.userservice.controller;

import com.example.userservice.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.example.userservice.util.JwtUtil;
import com.example.userservice.service.TokenBlacklistService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    // Endpoint: Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        try {
            // Single call to service that returns both user + token
            UserRegistrationResponse responseDTO = userService.createUserReturnToken(user);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
        }
    }

    // Endpoint: Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Call login service
            LoginResponse response = userService.loginUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint: Signout
    @PostMapping("/signout")
    public ResponseEntity<?> signout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            tokenBlacklistService.blacklistToken(jwtToken);
            return ResponseEntity.ok("You have been signed out successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid token.");
    }


    /*
    // Endpoint: Get user by username
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            // Return 404 Not Found if user does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

     */

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Password changed succesfully");
    }

}
