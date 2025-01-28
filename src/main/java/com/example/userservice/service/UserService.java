package com.example.userservice.service;

import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JwtUtil;
import com.example.userservice.dto.UserRegistrationResponse;
import com.example.userservice.dto.UserDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogProducerService logProducerService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRegistrationResponse createUserReturnToken(User user) {
        // check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("The username " + user.getUsername() + " is already taken. Please choose a different username.");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("The email " + user.getEmail() + " is already registered. Please use a different email.");
        }


        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        User savedUser = userRepository.save(user);

        // Log to RabbitMQ
        logProducerService.sendLog(
                "UserService", //"Service" field
                "Info", // "LogLevel"
                "Created user: " + savedUser.getUsername() // "Message"
        );

        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getUsername());

        // Create UserDTO
        UserDTO userDTO = new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());

        // Create and return response
        return new UserRegistrationResponse(userDTO, token);
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        // Find the user by username or email
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        // Validate the password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Return LoginResponse
        return new LoginResponse(token, "Login successful");
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
