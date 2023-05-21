package com.ems.controller;

import java.util.List;

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

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.exception.LeaveException;
import com.ems.exception.SalaryException;
import com.ems.model.DateRange;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.model.Leaves;
import com.ems.model.Salary;
import com.ems.service.EmployeeService;
import com.ems.service.LeavesService;
import com.ems.service.SalaryService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SalaryService salaryService;
	
	@Autowired
	private LeavesService leavesService;
	
	
	@PostMapping("")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) throws EmployeeException{
	
		Employee persistedEmployee = employeeService.addEmployee(employee);
		
		return new ResponseEntity<Employee>(persistedEmployee,HttpStatus.CREATED);

	}
	
	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById( @PathVariable Integer employeeId) throws EmployeeException{
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		return new ResponseEntity<Employee>(employee,HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Integer employeeId) throws EmployeeException, DocumentException{
		
		Employee persistedEmployee = employeeService.updateEmployeeById(employee, employeeId);
		
		return new ResponseEntity<Employee>(persistedEmployee,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{employeeId}/salary")
	public ResponseEntity<List<Salary>> getSalaryByEmployeeIdWSalary(@PathVariable Integer employeeId) throws EmployeeException{
		
		List<Salary> listOfSalaries = salaryService.getSalaryByEmployeeId(employeeId);
		return new ResponseEntity<List<Salary>>(listOfSalaries, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{employeeId}/salary/{salaryId}")
	public ResponseEntity<Salary> getSalaryByEmployeeIdAndSalaryId(@PathVariable Integer employeeId, @PathVariable Integer salaryId) throws EmployeeException, SalaryException{
		
		Salary salary = salaryService.getSalaryBySalaryIdAndEmployeeId(employeeId, salaryId);
		return new ResponseEntity<Salary>(salary, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{employeeId}/leave")
	public ResponseEntity<List<Leaves>> getLeaveByEmployeeIdWLeave(@PathVariable Integer employeeId) throws LeaveException{
		
		List<Leaves> listOfLeaves = leavesService.getLeaveByEmployeeId(employeeId);
		return new ResponseEntity<List<Leaves>>(listOfLeaves, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{employeeId}/leave/{leaveId}")
	public ResponseEntity<Leaves> getLeaveByEmployeeIdAndLeaveId(@PathVariable Integer employeeId, @PathVariable Integer leaveId) throws EmployeeException, LeaveException{
		
		Leaves leaves = leavesService.getLeaveByLeaveIdAndEmployeeId(employeeId, leaveId);
		return new ResponseEntity<Leaves>(leaves, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{employeeId}/getDocuments")
	public ResponseEntity<List<Document>> getAllDocumentsOfAnEmployeeByEmployeeId(@PathVariable Integer employeeId) throws DocumentException, EmployeeException{
		
		List<Document> listOfDocuments = employeeService.getAlldocumentsById(employeeId);
		return new ResponseEntity<List<Document>>(listOfDocuments, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/{employeeId}/getLeaves")
	public ResponseEntity<List<Leaves>> getListOfLeavesForEmployeeWithinDateRange(@RequestBody DateRange dateRange, @PathVariable Integer employeeId)
	{
		
		List<Leaves> answer = employeeService.getAllLeavesByEmployeeIdWithinDateRange(employeeId, dateRange);
		return new ResponseEntity<List<Leaves>>(answer, HttpStatus.ACCEPTED);
	}
	
	
}
	

