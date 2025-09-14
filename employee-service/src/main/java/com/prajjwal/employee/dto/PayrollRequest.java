package com.prajjwal.employee.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollRequest {

    private Long employeeId;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
}

