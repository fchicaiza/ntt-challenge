package com.ntt.banking.infrastructure.persistence.repository;

import com.ntt.banking.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaMovementRepository extends JpaRepository<MovementEntity, UUID> {
    List<MovementEntity> findByAccountIdOrderByDateTimeDesc(String accountId);

    List<MovementEntity> findByAccountIdAndDateTimeBetween(String accountId, LocalDateTime start, LocalDateTime end);
}
