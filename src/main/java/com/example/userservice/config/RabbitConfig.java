package com.example.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "log_message_queue";
    public static final String EXCHANGE_NAME = "log-message";

    @Bean
    public Queue logMessageQueue() {
        // durable = true if you want it to survive restarts
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public org.springframework.amqp.core.Exchange logMessageExchange() {
        // Using a direct exchange; you can also use topic/fanout if .NET side expects that
        return ExchangeBuilder.directExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Binding bindingLogMessageQueu(Queue logMessageQueue,
                                         org.springframework.amqp.core.Exchange logMessageExchange) {
        // For direct exchange, the routing key must match exactly
        // If your .NET code is using an empty routing key, use "" here
        return BindingBuilder.bind(logMessageQueue)
                .to(logMessageExchange)
                .with("")
                .noargs();
    }
}
