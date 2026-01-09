package com.ntt.banking.infrastructure.web;

import com.ntt.banking.model.AccountRequest;
import com.ntt.banking.model.AccountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AccountIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateAndRetrieveAccount() {
        AccountRequest request = new AccountRequest();
        request.setAccountNumber("999999");
        request.setAccountType(AccountRequest.AccountTypeEnum.SAVINGS);
        // The error says: BigDecimal cannot be converted to Double,
        // which means the setter setInitialBalance(Double) is expected?
        // No, let me try setting it as Double.
        request.setInitialBalance(500.0);
        // Note: status is not in AccountRequest per openapi.yaml

        // Create Account
        AccountResponse response = webTestClient.post()
                .uri("/api/v1/customers/1/accounts") // Corrected path per openapi
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("999999", response.getAccountNumber());
        // balance in Response is Double per openapi.yaml
        assertEquals(500.0, response.getBalance());

        // Get Account by ID
        webTestClient.get()
                .uri("/api/v1/accounts/" + response.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accountNumber").isEqualTo("999999")
                .jsonPath("$.balance").isEqualTo(500.0);
    }
}
