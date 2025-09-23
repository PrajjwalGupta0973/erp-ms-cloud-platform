package org.prajjwal.funqygooglecloudfunctions;

import java.math.BigDecimal;

import org.prajjwal.funqygooglecloudfunctions.dto.PayrollRequest;
import org.prajjwal.funqygooglecloudfunctions.dto.PayrollResult;

import io.quarkus.funqy.Funq;

public class PayrollFunction {

	@Funq("calculate")
	public PayrollResult calculatePayroll(PayrollRequest request) {
		BigDecimal gross = request.getBaseSalary().add(request.getBonus());
		BigDecimal tax = gross.multiply(BigDecimal.valueOf(0.20));
		BigDecimal net = gross.subtract(tax);

		return new PayrollResult(request.getEmployeeId(), request.getBaseSalary(), request.getBonus(), gross, net, tax);
	}
}
