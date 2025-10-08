package com.prajjwal.employee.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.prajjwal.employee.dto.PayrollRequest;
import com.prajjwal.employee.dto.PayrollResult;

import reactor.core.publisher.Mono;

@Component
public class PayrollClient {

	private final WebClient webClient;

	public PayrollClient(@Value("${externalServices.payrollCalculator}") String payrollServiceEndpoint) {
		this.webClient = WebClient.builder().baseUrl(payrollServiceEndpoint).build();
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
