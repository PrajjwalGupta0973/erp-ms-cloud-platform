package com.prajjwal.projectservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prajjwal.projectservice.entity.Project;
import com.prajjwal.projectservice.entity.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

}
