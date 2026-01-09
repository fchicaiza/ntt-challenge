package com.ntt.banking.infrastructure.web;

import com.ntt.banking.api.CustomersApi;
import com.ntt.banking.application.service.CustomerService;
import com.ntt.banking.domain.customer.Customer;
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

    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(Mono<CustomerRequest> customerRequest,
            ServerWebExchange exchange) {
        return customerRequest
                .map(this::mapToDomain)
                .flatMap(customerService::createCustomer)
                .map(this::mapToResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(String customerId, ServerWebExchange exchange) {
        return customerService.getCustomerById(customerId)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(String customerId,
            Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest
                .map(this::mapToDomain)
                .flatMap(domain -> customerService.updateCustomer(customerId, domain))
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String customerId, ServerWebExchange exchange) {
        return customerService.deleteCustomer(customerId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getAllCustomers(ServerWebExchange exchange) {
        Flux<CustomerResponse> responseFlux = customerService.getAllCustomers()
                .map(this::mapToResponse);
        return Mono.just(ResponseEntity.ok(responseFlux));
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
        response.setId(customer.getId().getValue().toString());
        response.setName(customer.getName());
        response.setGender(customer.getGender());
        response.setIdentification(customer.getIdentification());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setStatus(customer.isActive());
        return response;
    }
}
