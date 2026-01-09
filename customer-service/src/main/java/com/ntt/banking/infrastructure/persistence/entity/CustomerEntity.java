package com.ntt.banking.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity extends PersonEntity {

    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}
