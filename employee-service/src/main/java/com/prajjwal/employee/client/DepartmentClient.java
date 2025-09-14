package com.prajjwal.employee.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.prajjwal.employee.dto.DepartmentDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DepartmentClient {

    private final WebClient webClient;

    public DepartmentClient() {
        // "company-service" is the Kubernetes service name
        this.webClient = WebClient.builder()
                                  .baseUrl("http://localhost:8081") 
                                  .build();
    }

    public Mono<DepartmentDto> getDepartmentById(Long departmentId) {
        return webClient.get()
                        .uri("/departments/{id}", departmentId)
                        .accept(MediaType.APPLICATION_JSON, MediaType.valueOf("application/json;charset=UTF-8"))
                        .retrieve()
                        .bodyToMono(DepartmentDto.class);
    }
    public Flux<DepartmentDto> streamDepartments() {
        return webClient.get()
                .uri("/departments")
                .accept(MediaType.TEXT_EVENT_STREAM)  // Accept SSE
                .retrieve()
                .bodyToFlux(DepartmentDto.class);     // Flux because multiple events
    }

}
