package com.example.userservice.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    // The .NET code sets up "log_message_queue" bound to the "log-message" exchange
    private static final String EXCHANGE_NAME = "log-message";
    private static final String ROUTING_KEY = ""; // empty, unless there's a specific key


    /**
     * Publish a log message matching the .NET consumer's JObject structure
     *
     * Example JSON that .NET expects:
     * {
     *     "Service": "UserService",
     *     "Content": {
     *         "LogLevel": "Info",
     *         "Message": "Some message"
     *     }
     * }
     */

    public void sendLog(String serviceName, String logLevel, String message) {
        Map<String, Object> msgPayload = new HashMap<>();
        msgPayload.put("Service", serviceName);

        // "Content" must be an object with "LogLevel" and "Message"
        Map<String, Object> content = new HashMap<>();
        content.put("LogLevel", logLevel);
        content.put("Message", message);

        msgPayload.put("Content", content);

        // Publish to RabbitMQ: Exchange="log-message", RoutingKey=""
        amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, msgPayload);

        System.out.println("Sent log message to RabbitMQ: " + msgPayload);
    }

}
