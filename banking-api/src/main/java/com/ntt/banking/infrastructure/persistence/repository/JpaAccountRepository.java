package com.ntt.banking.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.ntt.banking.infrastructure.persistence.entity.AccountEntity;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
}
