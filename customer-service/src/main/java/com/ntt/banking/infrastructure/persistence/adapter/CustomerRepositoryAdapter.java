package com.ntt.banking.infrastructure.persistence.adapter;

import com.ntt.banking.application.port.out.CustomerRepositoryPort;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;
import com.ntt.banking.infrastructure.persistence.entity.CustomerEntity;
import com.ntt.banking.infrastructure.persistence.repository.JpaCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = mapToEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return jpaCustomerRepository.findById(id.getValue())
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Customer> findByIdentification(String identification) {
        return jpaCustomerRepository.findByIdentification(identification)
                .map(this::mapToDomain);
    }

    @Override
    public void deleteById(CustomerId id) {
        jpaCustomerRepository.deleteById(id.getValue());
    }

    private CustomerEntity mapToEntity(Customer customer) {
        return CustomerEntity.builder()
                .customerId(customer.getId().getValue())
                .name(customer.getName())
                .gender(customer.getGender())
                .identification(customer.getIdentification())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .password(customer.getPassword())
                .status(customer.isActive())
                .build();
    }

    private Customer mapToDomain(CustomerEntity entity) {
        return new Customer(
                new CustomerId(entity.getCustomerId()),
                entity.getName(),
                entity.getGender(),
                entity.getIdentification(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getPassword(),
                entity.getStatus());
    }
}
