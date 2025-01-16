package com.example.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JwtUtil;
import com.example.userservice.dto.UserRegistrationResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Add this line to inject the producer
    @Autowired
    private LogProducerService logProducerService;

    // add jwt utility
    @Autowired
    private JwtUtil jwtUtil;

    public UserRegistrationResponse createUserReturnToken(User user) {
        // 1) Save user to DB
        User savedUser = userRepository.save(user);

        // 2) Produce a log message to RabbitMQ
        logProducerService.sendLog(
                "UserService", //"Service" field
                "Info", // "LogLevel"
                "Created user: " + savedUser.getUsername() // "Message"
        );

        // 3) Generate a JWT token for this user
        String token = jwtUtil.generateToken(savedUser.getUsername());

        // 3) Build and return some custom response
        return new UserRegistrationResponse(token, "User created successfully!");
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
