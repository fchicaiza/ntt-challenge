package com.ntt.banking.application.port.out;

import java.util.Optional;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;

public interface CustomerRepositoryPort {

    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId id);

    Optional<Customer> findByIdentification(String identification);

    java.util.List<Customer> findAll();

    void delete(CustomerId id);
}
