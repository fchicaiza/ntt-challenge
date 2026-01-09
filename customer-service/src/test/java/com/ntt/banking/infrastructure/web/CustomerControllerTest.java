package com.ntt.banking.infrastructure.web;

import com.ntt.banking.application.service.CustomerService;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;
import com.ntt.banking.model.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CustomerService customerService;

    @Test
    void createCustomer_ShouldReturnCreated_WhenValidRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setName("Jose Lema");
        request.setGender("M");
        request.setIdentification("1712345678");
        request.setAddress("Otavalo");
        request.setPhone("098254785");
        request.setPassword("1234");
        request.setStatus(true);

        Customer createdCustomer = Customer.create(
                request.getName(),
                request.getGender(),
                request.getIdentification(),
                request.getAddress(),
                request.getPhone(),
                request.getPassword());

        Mockito.when(customerService.createCustomer(any(Customer.class)))
                .thenReturn(Mono.just(createdCustomer));

        webTestClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Jose Lema")
                .jsonPath("$.identification").isEqualTo("1712345678");
    }
}
