package com.ntt.banking.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;
}
