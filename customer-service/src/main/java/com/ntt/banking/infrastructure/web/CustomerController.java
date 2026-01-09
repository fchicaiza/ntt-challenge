package com.ntt.banking.infrastructure.web;

import com.ntt.banking.api.CustomersApi;
import com.ntt.banking.application.usecase.CreateCustomerUseCase;
import com.ntt.banking.application.usecase.DeleteCustomerUseCase;
import com.ntt.banking.application.usecase.GetCustomerUseCase;
import com.ntt.banking.application.usecase.UpdateCustomerUseCase;
import com.ntt.banking.domain.customer.Customer;
import com.ntt.banking.domain.customer.CustomerId;
import com.ntt.banking.model.CustomerRequest;
import com.ntt.banking.model.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(Mono<CustomerRequest> customerRequest,
            ServerWebExchange exchange) {
        return customerRequest
                .map(this::mapToDomain)
                .map(createCustomerUseCase::execute)
                .map(this::mapToResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String customerId, ServerWebExchange exchange) {
        return Mono.fromRunnable(() -> deleteCustomerUseCase.execute(customerId))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(String customerId, ServerWebExchange exchange) {
        return Mono.justOrEmpty(getCustomerUseCase.findById(customerId))
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getAllCustomers(ServerWebExchange exchange) {
        Flux<CustomerResponse> customers = Flux.fromIterable(getCustomerUseCase.findAll())
                .map(this::mapToResponse);
        return Mono.just(ResponseEntity.ok(customers));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(String customerId,
            Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest
                .map(this::mapToDomain)
                .map(customer -> updateCustomerUseCase.execute(customerId, customer))
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }

    private Customer mapToDomain(CustomerRequest request) {
        return Customer.create(
                request.getName(),
                request.getGender(),
                request.getIdentification(),
                request.getAddress(),
                request.getPhone(),
                request.getPassword());
    }

    private CustomerResponse mapToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId().value().toString());
        response.setName(customer.getName());
        response.setGender(customer.getGender());
        response.setIdentification(customer.getIdentification());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setStatus(customer.isActive());
        return response;
    }
}
