package com.ntt.banking.infrastructure.web;

import com.ntt.banking.api.AccountsApi;
import com.ntt.banking.application.service.AccountService;
import com.ntt.banking.domain.account.Account;
import com.ntt.banking.model.AccountRequest;
import com.ntt.banking.model.AccountResponse;
import com.ntt.banking.model.AccountUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

    private final AccountService accountService;

    @Override
    public Mono<ResponseEntity<AccountResponse>> createAccount(String customerId, Mono<AccountRequest> accountRequest,
            ServerWebExchange exchange) {
        return accountRequest
                .map(req -> mapToDomain(req, customerId))
                .flatMap(accountService::createAccount)
                .map(this::mapToResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @Override
    public Mono<ResponseEntity<AccountResponse>> getAccountById(String accountId, ServerWebExchange exchange) {
        return accountService.getAccountById(accountId)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<AccountResponse>> updateAccount(String accountId,
            Mono<AccountUpdateRequest> accountUpdateRequest, ServerWebExchange exchange) {
        return accountUpdateRequest
                .map(req -> new Account(
                        accountId,
                        null, // Number typically not updated here
                        null, // Type not updated
                        null, // Initial balance not updated
                        req.getStatus(),
                        null // CustomerId not updated
                ))
                .flatMap(domain -> accountService.updateAccount(accountId, domain))
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<AccountResponse>>> getAccountsByCustomer(String customerId,
            ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(
                accountService.getAccountsByCustomer(customerId)
                        .map(this::mapToResponse)));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(String accountId, ServerWebExchange exchange) {
        return accountService.deleteAccount(accountId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    private Account mapToDomain(AccountRequest request, String customerId) {
        return new Account(
                UUID.randomUUID().toString(), // Generate simplified ID for now
                request.getAccountNumber(),
                request.getAccountType().toString(),
                BigDecimal.valueOf(request.getInitialBalance()),
                true, // Default status active on creation
                customerId);
    }

    private AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getInitialBalance().doubleValue()); // Map initial balance to current balance for
                                                                        // response
        response.setStatus(account.isActive());
        response.setCustomerId(account.getCustomerId());
        return response;
    }
}
