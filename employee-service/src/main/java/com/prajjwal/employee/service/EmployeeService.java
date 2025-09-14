package com.prajjwal.employee.service;

import org.springframework.stereotype.Service;

import com.prajjwal.employee.client.DepartmentClient;
import com.prajjwal.employee.client.PayrollClient;
import com.prajjwal.employee.client.ProjectClient;
import com.prajjwal.employee.dto.DepartmentDto;
import com.prajjwal.employee.dto.EmployeeDto;
import com.prajjwal.employee.dto.PayrollRequest;
import com.prajjwal.employee.dto.PayrollResult;
import com.prajjwal.employee.dto.ProjectDto;
import com.prajjwal.employee.entity.Employee;
import com.prajjwal.employee.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentClient departmentClient;
	private final ProjectClient projectClient;
	private final PayrollClient payrollClient;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentClient departmentClient,
			ProjectClient projectClient, PayrollClient payrollClient) {
		this.employeeRepository = employeeRepository;
		this.departmentClient = departmentClient;
		this.projectClient = projectClient;
		this.payrollClient = payrollClient;
	}

	public Mono<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
//		
//		 Mono<EmployeeDto>  a= employeeRepository.save(new Employee()).flatMap(savedEmployee -> Mono.zip(
//                departmentClient.getDepartmentById(1L),
//                projectClient.getProjectById(1L)
//            , (dept, proj) -> toEmployeeDto(null, dept, proj)));
//		 
		return payrollClient.calculatePayroll(
				PayrollRequest.builder().baseSalary(employeeDto.getBaseSalary()).bonus(employeeDto.getBonus()).build())
				.flatMap(payrollResult -> {
					Employee employee = Employee.builder().firstName(employeeDto.getFirstName())
							.lastName(employeeDto.getLastName()).email(employeeDto.getEmail())
							.departmentId(employeeDto.getDepartment().getId())
							.projectId(employeeDto.getProject().getProjectId()).baseSalary(employeeDto.getBaseSalary())
							.bonus(employeeDto.getBonus()).totalSalary(payrollResult.grossSalary()).build();
//					return employeeRepository.save(employee).flatMap(savedEmployee -> Mono.zip(
//			                departmentClient.getDepartmentById(savedEmployee.getDepartmentId()),
//			                projectClient.getProjectById(1L)
//			            , (dept, proj) -> toEmployeeDto(null, dept, proj)));

					return employeeRepository.save(employee)
							.flatMap(savedEmployee -> Mono.zip(
									departmentClient.getDepartmentById(savedEmployee.getDepartmentId()),
									projectClient.getProjectById(savedEmployee.getProjectId()),
									(dept, proj) -> toEmployeeDto(savedEmployee, dept, proj, payrollResult)));
				});

	}

	public Mono<EmployeeDto> getEmployeeById(Long employeeId) {
		return employeeRepository.findById(employeeId)
				.flatMap(emp -> departmentClient.getDepartmentById(emp.getDepartmentId())
						.zipWith(projectClient.getProjectById(emp.getProjectId()))
						.zipWith(payrollClient.calculatePayroll(
								PayrollRequest.builder().baseSalary(emp.getBaseSalary()).bonus(emp.getBonus()).build()))
						.map(tuple -> {
							DepartmentDto dept = tuple.getT1().getT1();
							ProjectDto proj = tuple.getT1().getT2();
							PayrollResult payrollResult = tuple.getT2();
							return toEmployeeDto(emp, dept, proj, payrollResult);
						}));
	}

	public Flux<DepartmentDto> streamDepartments() {
		return departmentClient.streamDepartments();
	}

	private EmployeeDto toEmployeeDto(Employee employee, DepartmentDto department, ProjectDto project,
			PayrollResult payrollResult) {
		return EmployeeDto.builder().employeeId(employee.getEmployeeId()).firstName(employee.getFirstName())
				.lastName(employee.getLastName()).email(employee.getEmail()).department(department).project(project)
				.baseSalary(employee.getBaseSalary()).bonus(employee.getBonus())
				.totalSalary(payrollResult.grossSalary()).build();
	}

}
