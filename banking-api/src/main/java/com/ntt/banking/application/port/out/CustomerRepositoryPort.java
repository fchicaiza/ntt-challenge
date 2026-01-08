package com.ntt.banking.application.port.out;

import java.util.Optional;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;

public interface CustomerRepositoryPort {

    // CAMBIO 1: Cambiar 'void' por 'Customer' para eliminar el Type mismatch
    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId id);

    // CAMBIO 2: Agregar este método para eliminar el error 'undefined'
    Optional<Customer> findByIdentification(String identification);
}