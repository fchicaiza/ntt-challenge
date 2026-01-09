package com.ntt.banking.application.usecase;

import com.ntt.banking.application.port.out.CustomerRepositoryPort;
import com.ntt.banking.domain.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public void execute(String id) {
        CustomerId customerId = new CustomerId(UUID.fromString(id));
        customerRepository.delete(customerId);
    }
}
