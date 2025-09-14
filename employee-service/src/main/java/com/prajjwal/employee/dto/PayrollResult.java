package com.prajjwal.employee.dto;


import java.math.BigDecimal;

public record PayrollResult(
        Long employeeId,
        BigDecimal baseSalary,
        BigDecimal bonus,
        BigDecimal grossSalary,
        BigDecimal netSalary,
        BigDecimal tax
) {}