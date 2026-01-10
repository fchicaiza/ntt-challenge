package com.ntt.banking.domain;

import org.junit.jupiter.api.Test;

import com.ntt.banking.domain.customer.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateActiveCustomer() {
        Customer customer = Customer.create(
                "Jose Lema",
                "M",
                "1712345678",
                "Otavalo",
                "098254785",
                "1234");

        assertTrue(customer.isActive());
        assertNotNull(customer.getId());
    }
}