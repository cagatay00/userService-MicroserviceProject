package com.example.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Add this line to inject the producer
    @Autowired
    private LogProducerService logProducerService;

    public User createUser(User user) {
        // 1) Save user to DB
        User savedUser = userRepository.save(user);

        // 2) Produce a log message to RabbitMQ
        logProducerService.sendLog(
                "UserService", //"Service" field
                "Info", // "LogLevel"
                "Created user: " + savedUser.getUsername() // "Message"
        );

        // 3) Return the newly created user
        return savedUser;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
