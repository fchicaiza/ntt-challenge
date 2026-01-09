package com.ntt.banking.infrastructure.web;

import com.ntt.banking.api.ReportsApi;
import com.ntt.banking.application.service.ReportService;
import com.ntt.banking.model.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportsApi {

    private final ReportService reportService;

    @Override
    public Mono<ResponseEntity<ReportResponse>> getReport(
            String customerId,
            LocalDate startDate,
            LocalDate endDate,
            ServerWebExchange exchange) {
        return reportService.generateReport(customerId, startDate, endDate)
                .map(ResponseEntity::ok);
    }
}
