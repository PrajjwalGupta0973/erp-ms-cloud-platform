package com.prajjwal.employee.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.prajjwal.employee.dto.PayrollRequest;
import com.prajjwal.employee.dto.PayrollResult;

import reactor.core.publisher.Mono;

@Component
public class PayrollClient {

	private final WebClient webClient;
	private static final String PAYROLL_SERVICE_BASE_URL = "http://host.docker.internal:8082";

	public PayrollClient() {
		this.webClient = WebClient.builder().baseUrl(PAYROLL_SERVICE_BASE_URL).build();
	}

	//@formatter:off
    public Mono<PayrollResult> calculatePayroll(PayrollRequest request) {
        return webClient.post()
                .uri("/calculatePayroll")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PayrollResult.class);
    }
    //@formatter:on
}
