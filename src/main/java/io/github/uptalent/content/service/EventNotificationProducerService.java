package io.github.uptalent.content.service;

import io.github.uptalent.content.model.common.EventNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventNotificationProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routing-key.event_notification}")
    private String eventNotificationRoutingKey;

    public void sendEventNotificationMsg(EventNotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, eventNotificationRoutingKey, message);
    }
}
