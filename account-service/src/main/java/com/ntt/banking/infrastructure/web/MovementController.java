package com.ntt.banking.infrastructure.web;

import com.ntt.banking.api.MovementsApi;
import com.ntt.banking.application.service.MovementService;
import com.ntt.banking.domain.movement.Movement;
import com.ntt.banking.model.MovementRequest;
import com.ntt.banking.model.MovementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
@RequiredArgsConstructor
public class MovementController implements MovementsApi {

    private final MovementService movementService;

    @Override
    public Mono<ResponseEntity<MovementResponse>> createMovement(String accountId,
            Mono<MovementRequest> movementRequest,
            ServerWebExchange exchange) {
        return movementRequest
                .map(request -> mapToDomain(request, accountId))
                .flatMap(movement -> movementService.createMovement(accountId, movement))
                .map(this::mapToResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @Override
    public Mono<ResponseEntity<MovementResponse>> updateMovement(String movementId,
            Mono<com.ntt.banking.model.UpdateMovementRequest> updateMovementRequest, ServerWebExchange exchange) {
        return updateMovementRequest
                .flatMap(req -> movementService.updateMovement(movementId, req.getDescription()))
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovement(String movementId, ServerWebExchange exchange) {
        return movementService.deleteMovement(movementId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<MovementResponse>>> getMovementsByAccount(String accountId, LocalDate fromDate,
            LocalDate toDate, ServerWebExchange exchange) {
        Flux<Movement> movementFlux;
        if (fromDate != null && toDate != null) {
            movementFlux = movementService.getMovementsByAccountAndDate(accountId, fromDate.atStartOfDay(),
                    toDate.atTime(23, 59, 59));
        } else {
            movementFlux = movementService.getMovementsByAccount(accountId);
        }

        return Mono.just(ResponseEntity.ok(movementFlux.map(this::mapToResponse)));
    }

    private Movement mapToDomain(MovementRequest request, String accountId) {
        return Movement.create(
                request.getType().toString(),
                BigDecimal.valueOf(request.getAmount()),
                BigDecimal.ZERO, // Balance calculated in service
                request.getDescription(),
                accountId);
    }

    private MovementResponse mapToResponse(Movement movement) {
        MovementResponse response = new MovementResponse();
        response.setId(movement.getId());
        response.setAccountId(movement.getAccountId());
        response.setType(movement.getType());
        response.setAmount(movement.getAmount().doubleValue());
        response.setBalanceAfter(movement.getBalance().doubleValue());
        response.setDescription(movement.getDescription());
        response.setCreatedAt(OffsetDateTime.of(movement.getDateTime(), ZoneOffset.UTC));
        return response;
    }
}
