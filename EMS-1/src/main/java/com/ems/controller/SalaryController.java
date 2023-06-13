package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.exception.SalaryException;
import com.ems.model.Salary;
import com.ems.model.SalaryDTO;
import com.ems.service.SalaryService;

@RestController
@RequestMapping("/salary")
public class SalaryController {

	@Autowired
	private SalaryService salaryService;
	
	@PostMapping("/{documentId}")
	public ResponseEntity<Salary> addSalary(@RequestBody Salary salary, @PathVariable Integer documentId) throws SalaryException, EmployeeException, DocumentException {
		
		Salary persistedSalary = salaryService.addSalary(salary, documentId);
		return new ResponseEntity<Salary>(persistedSalary, HttpStatus.CREATED);
	}
	
	@GetMapping("/{salaryId}")
	public ResponseEntity<Salary> getSalaryById(@PathVariable Integer salaryId) throws SalaryException{
		
		Salary salary = salaryService.getSalaryById(salaryId);
		return new ResponseEntity<Salary>(salary, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{salaryId}")
	public ResponseEntity<SalaryDTO> deleteSalaryById(@PathVariable Integer salaryId) throws SalaryException{
		
		SalaryDTO salaryDTO = salaryService.deleteSalaryBySalaryId(salaryId);
		return new ResponseEntity<SalaryDTO>(salaryDTO, HttpStatus.ACCEPTED);
		
	}
	
}
