package com.ntt.banking.application.service;

import com.ntt.banking.domain.account.Account;
import com.ntt.banking.domain.movement.Movement;
import com.ntt.banking.model.CustomerResponse;
import com.ntt.banking.model.ReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private MovementService movementService;

    @Mock
    private WebClient customerWebClient;

    @InjectMocks
    private ReportService reportService;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock WebClient chain
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(customerWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void shouldGenerateReport() {
        String customerId = "c123";
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();

        CustomerResponse customer = new CustomerResponse();
        customer.setName("Jose Lema");

        Account account = new Account("a123", "478758", "SAVINGS", BigDecimal.valueOf(100), BigDecimal.valueOf(2000),
                true, customerId);
        Movement movement = new Movement("m1", LocalDateTime.now(), "DEBIT", BigDecimal.valueOf(100),
                BigDecimal.valueOf(1900), "Withdrawal", "a123");

        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(customerWebClient.get().uri(anyString(), anyString()).retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CustomerResponse.class)).thenReturn(Mono.just(customer));

        when(accountService.getAccountsByCustomer(customerId)).thenReturn(Flux.just(account));
        when(movementService.getMovementsByAccountAndDate(anyString(), any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(Flux.just(movement));

        StepVerifier.create(reportService.generateReport(customerId, start, end))
                .expectNextMatches(report -> {
                    return report.getCustomerName().equals("Jose Lema") &&
                            report.getAccounts().size() == 1 &&
                            report.getAccounts().get(0).getMovements().size() == 1;
                })
                .verifyComplete();
    }
}
