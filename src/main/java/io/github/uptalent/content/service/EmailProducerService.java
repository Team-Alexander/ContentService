package io.github.uptalent.content.service;

import io.github.uptalent.starter.model.common.EmailMessageGeneralInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routing-key.blocked-content}")
    private String blockedContentRoutingKey;
    @Value("${rabbitmq.routing-key.unblocked-content}")
    private String unblockedContentRoutingKey;

    public void sendBlockedContentMsg(EmailMessageGeneralInfo emailMessage) {
        rabbitTemplate.convertAndSend(exchange, blockedContentRoutingKey, emailMessage);
    }

    public void sendUnblockedContentMsg(EmailMessageGeneralInfo emailMessage) {
        rabbitTemplate.convertAndSend(exchange, unblockedContentRoutingKey, emailMessage);
    }
}
