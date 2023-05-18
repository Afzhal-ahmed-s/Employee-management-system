package com.ems.service;

import org.springframework.stereotype.Service;

import com.ems.exception.EmployeeException;
import com.ems.model.Employee;
import com.ems.model.EmployeeInputFormat;

@Service
public interface EmployeeService {

	public Employee addEmployee(EmployeeInputFormat employee)throws EmployeeException;
	
	public Employee getEmployeeById(Integer employeeId)throws EmployeeException;
	
	public Employee updateEmployeeById(EmployeeInputFormat employee, Integer employeeId)throws EmployeeException;
	
}
