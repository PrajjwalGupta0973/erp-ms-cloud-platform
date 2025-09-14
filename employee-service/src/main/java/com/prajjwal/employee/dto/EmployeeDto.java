package com.prajjwal.employee.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {

	private Long employeeId;
	private String email;
	private String firstName;
	private String lastName;
	private ProjectDto project;
	private DepartmentDto department;
	private BigDecimal baseSalary;
	private BigDecimal bonus;
	private BigDecimal totalSalary;
}
