package com.prajjwal.employee.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.prajjwal.employee.dto.ProjectDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProjectClient {

    private final WebClient.Builder webClientBuilder;

    private static final String PROJECT_SERVICE_BASE_URL = "http://host.docker.internal:8083/api/v1/projects";

    public Mono<ProjectDto> getProjectById(Long projectId) {
        return webClientBuilder.build()
                .get()
                .uri(PROJECT_SERVICE_BASE_URL + "/{id}", projectId)
                .retrieve()
                .bodyToMono(ProjectDto.class);
    }
}
