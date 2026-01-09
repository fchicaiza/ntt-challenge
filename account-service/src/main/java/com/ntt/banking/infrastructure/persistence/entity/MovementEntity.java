package com.ntt.banking.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "movement_type", nullable = false)
    private String movementType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "description")
    private String description;

    @Column(name = "account_id", nullable = false)
    private String accountId;
}
