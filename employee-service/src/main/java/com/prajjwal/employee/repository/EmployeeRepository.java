package com.prajjwal.employee.repository;

import java.util.Optional;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.prajjwal.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
