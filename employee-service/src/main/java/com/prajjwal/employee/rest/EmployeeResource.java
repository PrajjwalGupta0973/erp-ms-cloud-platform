package com.prajjwal.employee.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwal.employee.dto.DepartmentDto;
import com.prajjwal.employee.dto.EmployeeDto;
import com.prajjwal.employee.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    public Mono<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.createEmployee(employeeDto);
    }

    // Fetch an employee by ID
    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping(value = "/stream-departments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DepartmentDto> streamDepartments() {
		return employeeService.streamDepartments();
	}

}
