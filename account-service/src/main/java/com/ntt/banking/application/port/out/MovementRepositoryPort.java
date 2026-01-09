package com.ntt.banking.application.port.out;

import com.ntt.banking.domain.movement.Movement;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MovementRepositoryPort {
    Movement save(Movement movement);

    Flux<Movement> findByAccountIdAndDateBetween(String accountId, LocalDateTime start, LocalDateTime end);

    Optional<Movement> findById(String movementId);

    void delete(String movementId);

    Flux<Movement> findByAccountId(String accountId);
}
