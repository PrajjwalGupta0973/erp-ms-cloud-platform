package com.prajjwal.projectservice.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjwal.projectservice.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}