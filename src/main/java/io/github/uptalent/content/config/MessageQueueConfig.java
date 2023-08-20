package io.github.uptalent.content.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.queue.event_notification}")
    private String eventNotificationQueue;
    @Value("${rabbitmq.routing-key.event_notification}")
    private String eventNotificationRoutingKey;

    @Bean
    public Queue eventNotificationQueue() {
        return new Queue(eventNotificationQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding eventNotificationBinding() {
        return BindingBuilder
                .bind(eventNotificationQueue())
                .to(exchange())
                .with(eventNotificationRoutingKey);
    }
}
