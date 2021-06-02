package com.shelby.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 18:21
 * @Version 1.0
 */

@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue queue() {
        return new Queue("Queue1");
    }
}