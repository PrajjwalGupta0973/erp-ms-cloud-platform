package com.prajjwal.employee.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prajjwal.employee.client.CompanyClient;
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
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private CompanyClient companyClient;

	@Mock
	private ProjectClient projectClient;

	@Mock
	private PayrollClient payrollClient;

	@InjectMocks
	private EmployeeService employeeService;

	private static Employee employee;
    private static EmployeeDto employeeDto;
    private static DepartmentDto departmentDto;
    private static ProjectDto projectDto;
    private static PayrollResult payrollResult;

	@BeforeAll
	static void setUp() {

		departmentDto = DepartmentDto.builder().id(1L).departmentName("Engineering").build();

		projectDto = new ProjectDto();
		projectDto.setProjectId(10L);
		projectDto.setProjectName("ERP Platform");

		payrollResult = new PayrollResult(100L, BigDecimal.valueOf(100000), BigDecimal.valueOf(50000),
				BigDecimal.valueOf(150000), BigDecimal.valueOf(120000), BigDecimal.valueOf(30000));

		employee = Employee.builder().employeeId(100L).firstName("Prajjwal").lastName("Gupta")
				.email("Prajjwal.Gupta@example.com").departmentId(1L).projectId(10L)
				.baseSalary(BigDecimal.valueOf(100000)).bonus(BigDecimal.valueOf(50000))
				.totalSalary(BigDecimal.valueOf(150000)).build();

		employeeDto = EmployeeDto.builder().firstName("Prajjwal").lastName("Gupta").email("Prajjwal.Gupta@example.com")
				.baseSalary(BigDecimal.valueOf(100000)).bonus(BigDecimal.valueOf(50000)).department(departmentDto)
				.project(projectDto).build();
	}

	@Test
	void testCreateEmployee() {
		when(payrollClient.calculatePayroll(any(PayrollRequest.class))).thenReturn(Mono.just(payrollResult));

		when(employeeRepository.save(any(Employee.class))).thenReturn(Mono.just(employee));

		when(companyClient.getDepartmentById(1L)).thenReturn(Mono.just(departmentDto));

		when(projectClient.getProjectById(10L)).thenReturn(Mono.just(projectDto));

		Mono<EmployeeDto> result = employeeService.createEmployee(employeeDto);

		StepVerifier.create(result)
				.expectNextMatches(dto -> dto.getFirstName().equals("Prajjwal")
						&& dto.getTotalSalary().compareTo(BigDecimal.valueOf(150000)) == 0
						&& dto.getDepartment().getDepartmentName().equals("Engineering")
						&& dto.getProject().getProjectName().equals("ERP Platform"))
				.verifyComplete();
	}

	@Test
	void testGetEmployeeById() {
		when(employeeRepository.findById(100L)).thenReturn(Mono.just(employee));

		when(companyClient.getDepartmentById(1L)).thenReturn(Mono.just(departmentDto));

		when(projectClient.getProjectById(10L)).thenReturn(Mono.just(projectDto));

		when(payrollClient.calculatePayroll(any(PayrollRequest.class))).thenReturn(Mono.just(payrollResult));

		Mono<EmployeeDto> result = employeeService.getEmployeeById(100L);

		StepVerifier.create(result)
				.expectNextMatches(dto -> dto.getEmployeeId() == 100L
						&& dto.getTotalSalary().compareTo(BigDecimal.valueOf(150000)) == 0
						&& dto.getDepartment().getDepartmentName().equals("Engineering")
						&& dto.getProject().getProjectName().equals("ERP Platform"))
				.verifyComplete();
	}

	@Test
	void testStreamDepartments() {
		DepartmentDto d1 = DepartmentDto.builder().id(1L).departmentName("Engineering").build();
		DepartmentDto d2 = DepartmentDto.builder().id(2L).departmentName("Finance").build();

		when(companyClient.streamDepartments()).thenReturn(Flux.just(d1, d2));

		StepVerifier.create(employeeService.streamDepartments()).expectNext(d1).expectNext(d2).verifyComplete();
	}
}
