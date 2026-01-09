package com.ntt.banking.application.usecase;

import com.ntt.banking.application.port.out.CustomerRepositoryPort;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(new CustomerId(UUID.fromString(id)));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
