package com.ntt.banking.application.port.out;

import com.ntt.banking.domain.movement.Movement;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface MovementRepositoryPort {
    Movement save(Movement movement);

    Flux<Movement> findByAccountIdAndDateBetween(String accountId, LocalDateTime start, LocalDateTime end);

    Flux<Movement> findByAccountId(String accountId);
}
