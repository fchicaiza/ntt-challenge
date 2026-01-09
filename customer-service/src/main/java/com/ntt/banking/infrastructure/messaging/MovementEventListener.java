package com.ntt.banking.infrastructure.messaging;

import com.ntt.banking.application.dto.MovementEvent;
import com.ntt.banking.infrastructure.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovementEventListener {

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handleMovementCreated(MovementEvent event) {
        log.info(">>>> [AUDIT] Movement received for account {}: Type={}, Amount={}, BalanceAfter={}, Date={}",
                event.getAccountId(),
                event.getType(),
                event.getAmount(),
                event.getBalanceAfter(),
                event.getDateTime());
    }
}
