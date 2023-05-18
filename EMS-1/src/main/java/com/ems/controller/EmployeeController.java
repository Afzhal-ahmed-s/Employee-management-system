package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.exception.EmployeeException;
import com.ems.model.Employee;
import com.ems.model.EmployeeInputFormat;
import com.ems.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/status")
	public ResponseEntity<String>getStatus(){
		return new ResponseEntity<String>("Up",HttpStatus.ACCEPTED);
	}
	
	@PostMapping("")
	public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeInputFormat employee) throws EmployeeException{
	
		Employee persistedEmployee = employeeService.addEmployee(employee);
		
		return new ResponseEntity<Employee>(persistedEmployee,HttpStatus.CREATED);

	}
	
	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById( @PathVariable Integer employeeId) throws EmployeeException{
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		return new ResponseEntity<Employee>(employee,HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeInputFormat employee, @PathVariable Integer employeeId) throws EmployeeException{
		
		Employee persistedEmployee = employeeService.updateEmployeeById(employee, employeeId);
		
		return new ResponseEntity<Employee>(persistedEmployee,HttpStatus.CREATED);
	}
	
	
}
	

