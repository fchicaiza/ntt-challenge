package com.ntt.banking.application.service;

import com.ntt.banking.model.AccountReportResponse;
import com.ntt.banking.model.CustomerResponse;
import com.ntt.banking.model.MovementResponse;
import com.ntt.banking.model.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AccountService accountService;
    private final MovementService movementService;
    private final WebClient customerWebClient;

    public Mono<ReportResponse> generateReport(String customerId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Mono<String> customerNameMono = customerWebClient.get()
                .uri("/api/v1/customers/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .map(CustomerResponse::getName)
                .onErrorReturn("Unknown Customer");

        return customerNameMono.flatMap(customerName -> accountService.getAccountsByCustomer(customerId)
                .collectList()
                .flatMap(accounts -> {
                    Flux<AccountReportResponse> accountReportsFlux = Flux.fromIterable(accounts)
                            .flatMap(account -> movementService
                                    .getMovementsByAccountAndDate(account.getId(), startDateTime, endDateTime)
                                    .map(movement -> {
                                        MovementResponse mResp = new MovementResponse();
                                        mResp.setId(movement.getId());
                                        mResp.setAccountId(movement.getAccountId());
                                        mResp.setType(movement.getType());
                                        mResp.setAmount(movement.getAmount().doubleValue());
                                        mResp.setBalanceAfter(movement.getBalance().doubleValue());
                                        mResp.setDescription(movement.getDescription());
                                        mResp.setCreatedAt(movement.getDateTime().atOffset(ZoneOffset.UTC));
                                        return mResp;
                                    })
                                    .collectList()
                                    .map(movements -> {
                                        AccountReportResponse report = new AccountReportResponse();
                                        report.setAccountNumber(account.getAccountNumber());
                                        report.setAccountType(account.getAccountType());
                                        report.setInitialBalance(account.getInitialBalance());
                                        report.setCurrentBalance(account.getBalance());
                                        report.setStatus(account.isActive());
                                        report.setMovements(movements);
                                        return report;
                                    }));

                    return accountReportsFlux.collectList()
                            .map(accountReports -> {
                                ReportResponse response = new ReportResponse();
                                response.setCustomerName(customerName);
                                response.setReportDateRange(startDate + " to " + endDate);
                                response.setAccounts(accountReports);
                                return response;
                            });
                }));
    }
}
