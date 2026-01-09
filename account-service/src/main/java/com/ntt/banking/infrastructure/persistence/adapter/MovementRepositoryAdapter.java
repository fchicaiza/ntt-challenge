package com.ntt.banking.infrastructure.persistence.adapter;

import com.ntt.banking.application.port.out.MovementRepositoryPort;
import com.ntt.banking.domain.movement.Movement;
import com.ntt.banking.infrastructure.persistence.entity.MovementEntity;
import com.ntt.banking.infrastructure.persistence.repository.JpaMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final JpaMovementRepository jpaMovementRepository;

    @Override
    public Movement save(Movement movement) {
        MovementEntity entity = mapToEntity(movement);
        MovementEntity saved = jpaMovementRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Flux<Movement> findByAccountIdAndDateBetween(String accountId, LocalDateTime start, LocalDateTime end) {
        return Flux.fromIterable(jpaMovementRepository.findByAccountIdAndDateTimeBetween(accountId, start, end))
                .map(this::mapToDomain)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Movement> findByAccountId(String accountId) {
        return Flux.fromIterable(jpaMovementRepository.findByAccountIdOrderByDateTimeDesc(accountId))
                .map(this::mapToDomain)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private MovementEntity mapToEntity(Movement movement) {
        return MovementEntity.builder()
                .id(movement.getId() != null ? UUID.fromString(movement.getId()) : null)
                .dateTime(movement.getDateTime())
                .movementType(movement.getType())
                .amount(movement.getAmount())
                .balance(movement.getBalance())
                .description(movement.getDescription())
                .accountId(movement.getAccountId())
                .build();
    }

    private Movement mapToDomain(MovementEntity entity) {
        return new Movement(
                entity.getId().toString(),
                entity.getDateTime(),
                entity.getMovementType(),
                entity.getAmount(),
                entity.getBalance(),
                entity.getDescription(),
                entity.getAccountId());
    }

    @Override
    public java.util.Optional<Movement> findById(String movementId) {
        return jpaMovementRepository.findById(UUID.fromString(movementId)).map(this::mapToDomain);
    }

    @Override
    public void delete(String movementId) {
        jpaMovementRepository.deleteById(UUID.fromString(movementId));
    }
}
