package com.ntt.banking.domain.customer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomer() {
        // Asegúrate de que el método .create() en tu clase Customer sea estático
        Customer customer = Customer.create(
                "Jose Lema",
                "M",
                "1712345678",
                "Otavalo",
                "098254785",
                "dummyPass123");

        assertTrue(customer.isActive());
        assertNotNull(customer.getId());
    }
}