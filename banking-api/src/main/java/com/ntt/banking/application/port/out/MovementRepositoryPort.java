package com.ntt.banking.application.port.out;

import com.ntt.banking.domain.movement.Movement;

public interface MovementRepositoryPort {

    void save(Movement movement);
}
