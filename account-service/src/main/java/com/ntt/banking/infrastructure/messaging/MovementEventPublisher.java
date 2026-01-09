package com.ntt.banking.infrastructure.messaging;

import com.ntt.banking.application.dto.MovementEvent;
import com.ntt.banking.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovementEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishMovementCreated(MovementEvent event) {
        log.info("Publishing movement event for account: {}", event.getAccountId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
    }
}
