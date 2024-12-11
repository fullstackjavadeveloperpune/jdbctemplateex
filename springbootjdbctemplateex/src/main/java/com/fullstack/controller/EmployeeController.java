package com.fullstack.controller;

import com.fullstack.model.Employee;
import com.fullstack.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody Employee employee) {
		employeeService.signUp(employee);

		return ResponseEntity.ok("SignUp Done Successfully");
	}

	@GetMapping("/signin/{empEmailId}/{empPassword}")
	public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
		return ResponseEntity.ok(employeeService.signIn(empEmailId, empPassword));
	}

	@GetMapping("/findbyid/{empId}")
	public ResponseEntity<Employee> findById(@PathVariable int empId) {
		return ResponseEntity.ok(employeeService.findById(empId));
	}

	@GetMapping("/findall")
	public ResponseEntity<List<Employee>> findAll() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@GetMapping("/findbyname/{empName}")
	public ResponseEntity<List<Employee>> findByName(@PathVariable String empName) {
		return ResponseEntity
				.ok(employeeService.findAll().stream().filter(emp -> emp.getEmpName().equals(empName)).toList());
	}

	@GetMapping("/findbyanyinput/{input}")
	public ResponseEntity<List<Employee>> findByAnyInput(@PathVariable String input) {
		return ResponseEntity.ok(employeeService.findAll().stream()
				.filter(emp -> emp.getEmpName().equals(input) || String.valueOf(emp.getEmpId()).equals(input)
						|| String.valueOf(emp.getEmpContactNumber()).equals(input) || emp.getEmpEmailId().equals(input))
				.toList());
	}

	@GetMapping("/sortbysalary")
	public ResponseEntity<List<Employee>> sortBySalary() {
		return ResponseEntity
				.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList());
	}

	@GetMapping("/checkloaneligibility/{empId}")
	public ResponseEntity<Boolean> isEligibleForLoan(@PathVariable int empId) {
		Employee employee = employeeService.findById(empId);

		return ResponseEntity.ok(employee.getEmpSalary() >= 50000.00 ? true : false);
	}

	@PutMapping("/update/{empId}")
	public ResponseEntity<String> update(@PathVariable int empId, @RequestBody Employee employee) {
		employeeService.update(empId, employee);

		return ResponseEntity.ok("Data updated successfully");
	}

	@DeleteMapping("/deletebyid/{empId}")
	public ResponseEntity<String> deleteById(@PathVariable int empId) {
		employeeService.deleteById(empId);
		return ResponseEntity.ok("Data Deleted Successfully");
	}
}
