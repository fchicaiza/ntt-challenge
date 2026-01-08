package com.ntt.banking.application.port.out;

import com.ntt.banking.domain.customer.Customer;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Optional<Customer> findByIdentification(String identification);

    Customer save(Customer customer);

}
