package com.prajjwal.projectservice.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwal.projectservice.entity.Project;
import com.prajjwal.projectservice.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectResource {

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<Project> createProject(@RequestBody Project project) {
		Project created = projectService.createProject(project);
		return ResponseEntity.ok(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
		return projectService.getProjectById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}
