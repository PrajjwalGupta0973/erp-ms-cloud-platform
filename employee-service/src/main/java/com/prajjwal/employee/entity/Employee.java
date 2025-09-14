package com.prajjwal.employee.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

	@Id
	private Long employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private Long departmentId;
	private BigDecimal baseSalary;
	private BigDecimal bonus;
	private BigDecimal totalSalary;
	private Long projectId;
}
