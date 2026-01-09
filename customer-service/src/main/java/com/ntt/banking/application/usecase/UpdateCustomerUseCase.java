package com.ntt.banking.application.usecase;

import com.ntt.banking.application.port.out.CustomerRepositoryPort;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public Customer execute(String id, Customer customer) {
        CustomerId customerId = new CustomerId(UUID.fromString(id));
        return customerRepository.findById(customerId)
                .map(existing -> {
                    Customer updated = new Customer(
                            customerId,
                            customer.getName(),
                            customer.getGender(),
                            customer.getIdentification(),
                            customer.getAddress(),
                            customer.getPhone(),
                            customer.getPassword(),
                            customer.isActive());
                    return customerRepository.save(updated);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
