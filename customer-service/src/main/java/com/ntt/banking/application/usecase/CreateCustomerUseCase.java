package com.ntt.banking.application.usecase;

import com.ntt.banking.application.exception.CustomerAlreadyExistsException;
import com.ntt.banking.application.port.out.CustomerRepositoryPort;
import com.ntt.banking.domain.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public Customer execute(Customer customer) {
        log.info("Starting customer creation, identification={}", customer.getIdentification());

        customerRepository.findByIdentification(customer.getIdentification())
                .ifPresent(existing -> {
                    throw new CustomerAlreadyExistsException(
                            customer.getIdentification()
                    );
                });

        Customer saved = customerRepository.save(customer);

        log.info("Customer successfully created, id={}", saved.getId());
        return saved;
    }
}
